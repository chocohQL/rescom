package com.chocoh.ql.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.chocoh.ql.common.enums.FileAccessTypeEnum;
import com.chocoh.ql.domain.file.FileAccessRequest;
import com.chocoh.ql.domain.vo.FileInfo;
import com.chocoh.ql.service.file.chain.FileAccessChain;
import com.chocoh.ql.service.file.chain.FileResultChain;
import com.chocoh.ql.service.file.context.FileAccessContext;
import com.chocoh.ql.common.enums.FileTypeEnum;
import com.chocoh.ql.domain.file.FileUploadRequest;
import com.chocoh.ql.domain.file.FileUploadResult;
import com.chocoh.ql.domain.entity.FileDo;
import com.chocoh.ql.mapper.FileMapper;
import com.chocoh.ql.service.RepositoryService;
import com.chocoh.ql.service.BaseFileService;
import com.chocoh.ql.service.file.context.FileResultContext;
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
    private FileMapper fileMapper;
    @Resource
    private FileAccessChain fileAccessChain;
    @Resource
    private FileResultChain fileResultChain;

    @Override
    public FileUploadResult uploadFile(FileUploadRequest request) throws Exception {
        // 文件访问责任链：路径转换、仓库路径访问校验
        FileAccessContext context = fileAccessChain.execute(FileAccessTypeEnum.CREATE,
                request.getRepositoryId(), request.getPath(), request.getFileName());

        String path = context.getFilePath();
        String pathAndName = FilePathUtil.concat(path, request.getFile().getOriginalFilename());
        String userPath = getUserPath(pathAndName, request.getRepositoryId());

        if (request.getCurSlice() == null || request.getCurSlice() < 0) {
            // 普通上传
            File file = fileService.uploadFile(userPath, request.getFile().getInputStream());
            fileMapper.insert(getEasyFileDo(file, request.getRepositoryId(), path, request.getMd5()));
        } else {
            // 分片上传
            boolean isComplete = fileService.uploadFile(userPath, request.getCurSlice(), request.getSlices(), request.getSliceSize(), request.getFile().getBytes());
            if (isComplete) {
                fileMapper.insert(getEasyFileDo(new File(userPath), request.getRepositoryId(), path, request.getMd5()));
            }
        }
        return FileUploadResult.Complete();
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
        fileMapper.selectFileList(param.getRepositoryId(), context.getFilePath())
                .forEach(f -> fieldInfos.add(getFileInfo(f)));

        // 文件结果处理责任链：排序、文件隐藏
        FileResultContext result = fileResultChain.execute(fieldInfos, param);
        return result.getFileInfos();
    }

    private String getUserPath(String pathAndName, Long repositoryId) {
        return FilePathUtil.concat(String.valueOf(StpUtil.getLoginIdAsLong()), String.valueOf(repositoryId), pathAndName);
    }

    private FileInfo getFileInfo(FileDo fileDo) {
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileDo, fileInfo);
        return fileInfo;
    }

    private FileDo getEasyFileDo(File file, Long repositoryId, String path, String md5) {
        FileDo folder = fileMapper.selectFolder(repositoryId, path);
        return FileDo.builder()
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
