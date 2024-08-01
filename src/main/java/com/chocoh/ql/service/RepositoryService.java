package com.chocoh.ql.service;

import com.chocoh.ql.domain.file.FileAccessRequest;
import com.chocoh.ql.domain.file.FileUploadRequest;
import com.chocoh.ql.domain.file.FileUploadResult;
import com.chocoh.ql.domain.vo.FileInfo;

import java.util.List;

/**
 * @author chocoh
 */
public interface RepositoryService {
    FileUploadResult uploadFile(FileUploadRequest request) throws Exception;

    void download(String pathAndName, Long repositoryId) throws Exception;

    List<FileInfo> listFile(FileAccessRequest request) throws Exception;
}
