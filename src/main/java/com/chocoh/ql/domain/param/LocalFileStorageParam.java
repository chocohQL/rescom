package com.chocoh.ql.domain.param;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chocoh
 */
@Data
@Component
public class LocalFileStorageParam {
    @Value("${storage.local.filePath}")
    private String filePath;
    @Value("${storage.local.progressFileExt}")
    private String progressFileExt = ".tmp";
}
