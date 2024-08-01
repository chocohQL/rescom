package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.AccessLevelEnum;
import com.chocoh.ql.common.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_file")
public class FileDo implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 父文件id
     */
    private Long parentId;
    /**
     * 仓库id
     */
    private Long repositoryId;
    /**
     * 目录:0 文件:1
     */
    private FileTypeEnum type;
    /**
     * md5
     */
    private String md5;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小(kb)
     */
    private Long fileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 访问级别 0:公开 1:私有
     */
    private AccessLevelEnum accessLevel;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}