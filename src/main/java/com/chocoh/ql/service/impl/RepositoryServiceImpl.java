package com.chocoh.ql.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.chocoh.ql.common.enums.system.EventTypeEnum;
import com.chocoh.ql.common.enums.system.FileAccessTypeEnum;
import com.chocoh.ql.domain.request.FileAccessRequest;
import com.chocoh.ql.domain.dto.FileInfo;
import com.chocoh.ql.service.chain.FileAccessChain;
import com.chocoh.ql.service.chain.FileResultChain;
import com.chocoh.ql.domain.context.FileAccessContext;
import com.chocoh.ql.common.enums.system.FileTypeEnum;
import com.chocoh.ql.domain.request.FileUploadRequest;
import com.chocoh.ql.domain.dto.FileUploadResult;
import com.chocoh.ql.domain.entity.FileRecord;
import com.chocoh.ql.mapper.FileRecordMapper;
import com.chocoh.ql.service.RepositoryService;
import com.chocoh.ql.service.BaseFileService;
import com.chocoh.ql.domain.context.FileResultContext;
import com.chocoh.ql.service.event.EventManger;
import com.chocoh.ql.domain.context.EventContext;
import com.chocoh.ql.utils.FilePathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chocoh
 */
@Slf4j
@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Resource
    private BaseFileService fileService;
    @Resource
    private FileRecordMapper fileRecordMapper;
    @Resource
    private FileAccessChain fileAccessChain;
    @Resource
    private FileResultChain fileResultChain;
    @Resource
    private EventManger eventManger;

    @Override
    public FileUploadResult uploadFile(FileUploadRequest request) throws Exception {
        // 文件访问责任链：路径转换、仓库路径访问校验
        FileAccessContext context = fileAccessChain.execute(FileAccessTypeEnum.CREATE,
                request.getRepositoryId(), request.getPath(), request.getFileName());

        String path = context.getFilePath();
        String pathAndName = FilePathUtil.concat(path, request.getFile().getOriginalFilename());
        String userPath = getUserPath(pathAndName, request.getRepositoryId());

        boolean isComplete;
        if (request.getCurSlice() == null || request.getCurSlice() < 0) {
            // 普通上传
            isComplete = fileService.uploadFile(userPath, request.getFile().getInputStream());
        } else {
            // 分片上传
            isComplete = fileService.uploadFile(userPath, request.getCurSlice(), request.getSlices(), request.getSliceSize(), request.getFile().getBytes());
        }

        // 保存数据库
        if (isComplete) {
            FileRecord file = getEasyFileDo(new File(userPath), request.getRepositoryId(), path, request.getMd5());
            fileRecordMapper.insert(file);

            // 事件处理：日志、通知
            eventManger.push(EventContext.builder().eventType(EventTypeEnum.FILE_CREATE)
                    .repositoryId(request.getRepositoryId())
                    .userId(StpUtil.getLoginIdAsLong())
                    .fileId(file.getId()).build());
        }
        return FileUploadResult.builder().isComplete(true).build();
    }

    @Override
    public void download(String pathAndName, Long repositoryId) throws Exception {
        // 文件访问责任链：路径转换、仓库路径访问校验
        FileAccessContext context = fileAccessChain.execute(FileAccessTypeEnum.READ, repositoryId, pathAndName);

        // 文件下载
        fileService.download(getUserPath(context.getPathAndName(), repositoryId));
    }

    @Override
    public List<FileInfo> listFile(FileAccessRequest param) throws Exception {
        // 文件访问责任链：路径转换、仓库路径访问校验
        FileAccessContext context = fileAccessChain.execute(FileAccessTypeEnum.READ,
                param.getRepositoryId(), param.getPath(), param.getFileName());

        // 获取文件列表
        List<FileInfo> fieldInfos = new ArrayList<>();
        fileRecordMapper.selectFileList(param.getRepositoryId(), context.getFilePath())
                .forEach(f -> fieldInfos.add(getFileInfo(f)));

        // 文件结果处理责任链：排序、文件隐藏
        FileResultContext result = fileResultChain.execute(fieldInfos, param);
        return result.getFileInfos();
    }

    private String getUserPath(String pathAndName, Long repositoryId) {
        return FilePathUtil.concat(String.valueOf(StpUtil.getLoginIdAsLong()), String.valueOf(repositoryId), pathAndName);
    }

    private FileInfo getFileInfo(FileRecord fileRecord) {
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileRecord, fileInfo);
        return fileInfo;
    }

    private FileRecord getEasyFileDo(File file, Long repositoryId, String path, String md5) {
        FileRecord folder = fileRecordMapper.selectFolder(repositoryId, path);
        return FileRecord.builder()
                .userId(StpUtil.getLoginIdAsLong())
                .parentId(folder == null ? 0 : folder.getId())
                .filePath(folder == null ? "/" : folder.getFilePath())
                .type(file.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE)
                .repositoryId(repositoryId)
                .fileName(file.getName())
                .fileSize(file.length())
                .fileType(FileNameUtil.getSuffix(file.getName()))
                .md5(md5)
                .build();
    }
}
