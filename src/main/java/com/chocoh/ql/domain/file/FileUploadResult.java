package com.chocoh.ql.domain.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {
    private Boolean isComplete;
    // TODO 时间、未上传分片... 超时重传、断点续传等功能

    public static FileUploadResult Complete() {
        return FileUploadResult.builder().isComplete(true).build();
    }
}
