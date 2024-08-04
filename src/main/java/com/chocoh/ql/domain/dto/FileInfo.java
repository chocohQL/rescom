package com.chocoh.ql.domain.dto;

import com.chocoh.ql.common.enums.system.AccessLevelEnum;
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
public class FileInfo {
    private Long id;
    private Long userId;
    private Long parentId;
    private Long repositoryId;
    private FileTypeEnum type;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private Integer fileType;
    private AccessLevelEnum accessLevel;
    private Boolean hidden;
    private Date createTime;
    private Date updateTime;
}
