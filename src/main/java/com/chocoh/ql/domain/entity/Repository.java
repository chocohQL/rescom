package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.system.AccessLevelEnum;
import com.chocoh.ql.common.enums.system.LogLevelEnum;
import lombok.Data;

/**
 * 仓库表
 */
@Data
@TableName("t_repository")
public class Repository implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 描述
     */
    private String description;
    /**
     * 访问级别 0:公开 1:私有
     */
    private AccessLevelEnum accessLevel;
    /**
     * 日志级别
     */
    private LogLevelEnum logLevel;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}