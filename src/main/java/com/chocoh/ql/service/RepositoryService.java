package com.chocoh.ql.service;

import com.chocoh.ql.domain.request.FileAccessRequest;
import com.chocoh.ql.domain.request.FileUploadRequest;
import com.chocoh.ql.domain.dto.FileUploadResult;
import com.chocoh.ql.domain.dto.FileInfo;

import java.util.List;

/**
 * @author chocoh
 */
public interface RepositoryService {
    FileUploadResult uploadFile(FileUploadRequest request) throws Exception;

    void download(String pathAndName, Long repositoryId) throws Exception;

    List<FileInfo> listFile(FileAccessRequest request) throws Exception;
}
