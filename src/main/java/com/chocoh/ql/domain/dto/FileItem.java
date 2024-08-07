package com.chocoh.ql.domain.dto;

import com.chocoh.ql.common.enums.system.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileItem {
    private String name;
    private Date time;
    private Long size;
    private FileTypeEnum type;
    private String path;
    private String url;
}
