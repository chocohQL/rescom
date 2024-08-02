package com.chocoh.ql.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author chocoh
 */
public interface BaseFileService {
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
