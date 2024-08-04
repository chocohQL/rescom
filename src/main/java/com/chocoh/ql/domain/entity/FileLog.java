package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.system.EventTypeEnum;
import com.chocoh.ql.common.enums.system.LogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仓库日志表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_file_log")
public class FileLog implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 仓库id
     */
    private Long repositoryId;
    /**
     * 文件id
     */
    private Long fileId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 事件类型
     */
    private EventTypeEnum eventType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 时间
     */
    private Date time;
}