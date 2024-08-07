package com.chocoh.ql.service;

import com.chocoh.ql.domain.dto.FileItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author chocoh
 */
public interface BaseFileService {
    FileItem getFileItem(String pathAndName);

    List<FileItem> getFileList(String path);

    boolean uploadFile(String pathAndName, InputStream inputStream);

    boolean uploadFile(String pathAndName, Long curSlice, Long totalSlices, Long sliceSize, byte[] bytes) throws IOException;

    void download(String pathAndName);

    void downloadZip(String path);

    boolean newFolder(String pathAndName);

    boolean renameFile(String path, String name, String newName);

    boolean renameFolder(String path, String name, String newName);

    boolean deleteFile(String pathAndName);

    boolean deleteFolder(String pathAndName);
}
