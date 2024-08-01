package com.chocoh.ql.domain.file;

import cn.hutool.core.util.StrUtil;
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
public class FileAccessRequest {
    /**
     * 仓库id
     */
    private Long repositoryId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件id
     */
    private Long fileId;
    /**
     * 访问路径
     */
    private String path;
    /**
     * 搜索关键字
     */
    private String searchKey;
    /**
     * 排序字段（name\size\time）
     */
    private String orderBy;
    /**
     * 排序顺序（asc\desc）
     */
    private String orderDirection;

    public void setDefaultValue() {
        if (StrUtil.isEmpty(path)) {
            path = "/";
        }
        if (StrUtil.isEmpty(orderBy)) {
            orderBy = "name";
        }
        if (StrUtil.isEmpty(orderDirection)) {
            orderDirection = "asc";
        }
    }
}
