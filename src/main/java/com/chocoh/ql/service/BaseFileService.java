package com.chocoh.ql.service;

import com.chocoh.ql.domain.vo.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author chocoh
 */
public interface BaseFileService {
    List<FileInfo> fileList(String path);

    FileInfo getFileItem(String pathAndName);

    boolean newFolder(String path, String name);

    boolean deleteFile(String path, String name);

    boolean deleteFolder(String path, String name);

    boolean copyFile(String path, String name, String targetPath, String targetName);

    boolean copyFolder(String path, String name, String targetPath, String targetName);

    boolean moveFile(String path, String name, String targetPath, String targetName);

    boolean moveFolder(String path, String name, String targetPath, String targetName);

    boolean renameFile(String path, String name, String newName);

    boolean renameFolder(String path, String name, String newName);

    File uploadFile(String pathAndName, InputStream inputStream);

    boolean uploadFile(String pathAndName, Long slice, Long slices, Long sliceSize, byte[] bytes) throws IOException;

    void download(String pathAndName);
}
