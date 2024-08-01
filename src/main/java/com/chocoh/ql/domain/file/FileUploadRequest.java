package com.chocoh.ql.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest {
    /**
     * 仓库id
     */
    private Long repositoryId;
    /**
     * 上传路径
     */
    private String path;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 分片对象
     */
    private MultipartFile file;
    /**
     * md5
     */
    private String md5;
    /**
     * 分片长度
     */
    private Long size;
    /**
     * 分片数量
     */
    private Long slices;
    /**
     * >=0: 当前分片 <0: 不分片
     */
    private Long curSlice;
    /**
     * 分片大小
     */
    private Long sliceSize;
}
