package com.chocoh.ql.controller;

import com.alibaba.fastjson.JSON;
import com.chocoh.ql.domain.request.FileAccessRequest;
import com.chocoh.ql.domain.request.FileUploadRequest;
import com.chocoh.ql.domain.model.Response;
import com.chocoh.ql.service.RepositoryService;
import com.chocoh.ql.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author chocoh
 */
@Slf4j
@RestController
@RequestMapping("/repository")
public class RepositoryController {
    @Resource
    private RepositoryService repositoryService;

    @PostMapping("/file/upload")
    public Response uploadFile(@RequestParam("file") MultipartFile file,
                               @RequestParam("param") String param) throws Exception {
        FileUploadRequest fileUploadRequest = JSON.parseObject(param, FileUploadRequest.class);
        fileUploadRequest.setFile(file);
        return Response.success(repositoryService.uploadFile(fileUploadRequest));
    }

    @GetMapping("/file/download/{repositoryId}/**")
    public void downloadFile(@PathVariable Long repositoryId) throws Exception {
        String filePath = ServletUtil.getExtractPathWithinPattern();
        repositoryService.download(filePath, repositoryId);
    }

    @GetMapping("/files")
    public Response fileList(@RequestBody FileAccessRequest fileAccessRequest) throws Exception {
        return Response.success(repositoryService.listFile(fileAccessRequest));
    }
}
