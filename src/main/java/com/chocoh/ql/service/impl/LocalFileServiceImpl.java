package com.chocoh.ql.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.chocoh.ql.exception.file.FileDownloadErrorException;
import com.chocoh.ql.exception.file.FileNotFoundException;
import com.chocoh.ql.service.BaseFileService;
import com.chocoh.ql.utils.FilePathUtil;
import com.chocoh.ql.utils.ServletUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;

import static com.chocoh.ql.common.constant.Constants.STORAGE_BASE_PATH;

/**
 * @author chocoh
 */
@SuppressWarnings("DuplicatedCode")
@Service
public class LocalFileServiceImpl implements BaseFileService {


    @Override
    public boolean uploadFile(String pathAndName, InputStream inputStream) {
        FilePathUtil.checkPathSecurity(pathAndName);
        String uploadPath = getFinalPath(pathAndName);
        String parentPath = FileUtil.getParent(uploadPath, 1);
        if (!FileUtil.exist(parentPath)) {
            FileUtil.mkdir(parentPath);
        }
        File file = new File(uploadPath);
        BufferedOutputStream outputStream = FileUtil.getOutputStream(file);
        IoUtil.copy(inputStream, outputStream);
        IoUtil.close(outputStream);
        IoUtil.close(inputStream);
        return true;
    }

    @Override
    public boolean uploadFile(String pathAndName, Long curSlice, Long totalSlices, Long sliceSize, byte[] bytes) throws IOException {
        FilePathUtil.checkPathSecurity(pathAndName);
        String uploadPath = getFinalPath(pathAndName);
        String parentPath = FileUtil.getParent(uploadPath, 1);
        if (!FileUtil.exist(parentPath)) {
            FileUtil.mkdir(parentPath);
        }
        File file = new File(uploadPath);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long offset = sliceSize * curSlice;
            raf.seek(offset);
            raf.write(bytes);
        }
        File progressFile = new File(uploadPath + ".tmp");
        byte isComplete = Byte.MAX_VALUE;
        try (RandomAccessFile raf = new RandomAccessFile(progressFile, "rw")) {
            raf.setLength(totalSlices);
            raf.seek(curSlice);
            raf.write(Byte.MAX_VALUE);
            byte[] process = FileUtils.readFileToByteArray(progressFile);
            for (int i = 0; i < progressFile.length() && isComplete == Byte.MAX_VALUE; i++) {
                isComplete = (byte) (isComplete & process[i]);
            }
            if (isComplete == Byte.MAX_VALUE) {
                return FileUtil.del(progressFile);
            }
        }
        return false;
    }

    @Override
    public void download(String pathAndName) {
        FilePathUtil.checkPathSecurity(pathAndName);
        File file = new File(getFinalPath(pathAndName));
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
    public void downloadZip(String path) {
        FilePathUtil.checkPathSecurity(path);
        try {
            File zipFile = ZipUtil.zip(path);
            ServletUtil.writeFile(zipFile);
        } catch (IOException e) {
            throw new FileDownloadErrorException();
        }
    }

    @Override
    public boolean newFolder(String pathAndName) {
        FilePathUtil.checkPathSecurity(pathAndName);
        return FileUtil.mkdir(getFinalPath(pathAndName)) != null;
    }

    @Override
    public boolean renameFile(String path, String name, String newName) {
        FilePathUtil.checkPathSecurity(path);
        FilePathUtil.checkNameSecurity(name, newName);
        if (StrUtil.equals(name, newName)) {
            return true;
        }
        File file = new File(getFinalPath(path, name));
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileUtil.rename(file, newName, true);
        return false;
    }

    @Override
    public boolean renameFolder(String path, String name, String newName) {
        return renameFile(path, name, newName);
    }

    @Override
    public boolean deleteFile(String pathAndName) {
        FilePathUtil.checkPathSecurity(pathAndName);
        return FileUtil.del(getFinalPath(pathAndName));
    }

    @Override
    public boolean deleteFolder(String pathAndName) {
        return deleteFile(pathAndName);
    }

    private String getFinalPath(String... paths) {
        return FilePathUtil.concat(STORAGE_BASE_PATH, FilePathUtil.concat(paths));
    }
}

