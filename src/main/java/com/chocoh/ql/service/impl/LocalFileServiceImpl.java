package com.chocoh.ql.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.chocoh.ql.domain.vo.FileInfo;
import com.chocoh.ql.domain.param.LocalFileStorageParam;
import com.chocoh.ql.exception.file.FileDownloadErrorException;
import com.chocoh.ql.exception.file.FileNotFoundException;
import com.chocoh.ql.service.BaseFileService;
import com.chocoh.ql.utils.FilePathUtil;
import com.chocoh.ql.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author chocoh
 */
@Slf4j
@Service
@SuppressWarnings("DuplicatedCode")
public class LocalFileServiceImpl implements BaseFileService {
    @Resource
    private LocalFileStorageParam param;

    @Override
    public File uploadFile(String pathAndName, InputStream inputStream) {
        // 检查路径
        FilePathUtil.checkPathSecurity(pathAndName);
        String uploadPath = FilePathUtil.removeDuplicateSlashes(param.getFilePath() + pathAndName);
        // 创建目录
        String parentPath = FileUtil.getParent(uploadPath, 1);
        if (!FileUtil.exist(parentPath)) {
            FileUtil.mkdir(parentPath);
        }
        // 写入文件
        File file = new File(uploadPath);
        BufferedOutputStream outputStream = FileUtil.getOutputStream(file);
        IoUtil.copy(inputStream, outputStream);
        IoUtil.close(outputStream);
        IoUtil.close(inputStream);
        return file;
    }

    @Override
    public boolean uploadFile(String pathAndName, Long slice, Long slices, Long sliceSize, byte[] bytes) throws IOException {
        // 检查路径
        FilePathUtil.checkPathSecurity(pathAndName);
        String uploadPath = FilePathUtil.removeDuplicateSlashes(param.getFilePath() + pathAndName);
        // 创建目录
        String parentPath = FileUtil.getParent(uploadPath, 1);
        if (!FileUtil.exist(parentPath)) {
            FileUtil.mkdir(parentPath);
        }
        // 写入分片
        File file = new File(uploadPath);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long offset = sliceSize * slice;
            raf.seek(offset);
            raf.write(bytes);
        }
        // 检查是否上传完成
        File progressFile = new File(uploadPath + param.getProgressFileExt());
        byte isComplete = Byte.MAX_VALUE;
        try (RandomAccessFile raf = new RandomAccessFile(progressFile, "rw")) {
            raf.setLength(slices);
            raf.seek(slice);
            raf.write(Byte.MAX_VALUE);
            byte[] process = FileUtils.readFileToByteArray(progressFile);
            for (int i = 0; i < progressFile.length() && isComplete == Byte.MAX_VALUE; i++) {
                isComplete = (byte) (isComplete & process[i]);
            }
            // 删除临时文件
            if (isComplete == Byte.MAX_VALUE) {
                return FileUtil.del(progressFile);
            }
        }
        return false;
    }

    @Override
    public void download(String pathAndName) {
        FilePathUtil.checkPathSecurity(pathAndName);
        File file = new File(FilePathUtil.removeDuplicateSlashes(param.getFilePath() + pathAndName));
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        try {
            ServletUtil.writeFile(file);
        } catch (IOException e) {
            throw new FileDownloadErrorException();
        }
    }

    @Override
    public List<FileInfo> fileList(String path) {
        return null;
    }

    @Override
    public FileInfo getFileItem(String pathAndName) {
        return null;
    }

    @Override
    public boolean newFolder(String path, String name) {
        return false;
    }

    @Override
    public boolean deleteFile(String path, String name) {
        return false;
    }

    @Override
    public boolean deleteFolder(String path, String name) {
        return false;
    }

    @Override
    public boolean copyFile(String path, String name, String targetPath, String targetName) {
        return false;
    }

    @Override
    public boolean copyFolder(String path, String name, String targetPath, String targetName) {
        return false;
    }

    @Override
    public boolean moveFile(String path, String name, String targetPath, String targetName) {
        return false;
    }

    @Override
    public boolean moveFolder(String path, String name, String targetPath, String targetName) {
        return false;
    }

    @Override
    public boolean renameFile(String path, String name, String newName) {
        return false;
    }

    @Override
    public boolean renameFolder(String path, String name, String newName) {
        return false;
    }
}

