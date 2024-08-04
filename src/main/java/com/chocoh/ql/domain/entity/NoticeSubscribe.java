package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.system.EventTypeEnum;
import lombok.Data;

/**
 * 消息订阅表
 */
@Data
@TableName("t_notice_subscribe")
public class NoticeSubscribe implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 仓库id
     */
    private Long repositoryId;
    /**
     * 事件类型
     */
    private EventTypeEnum eventType;
    /**
     * 使用邮箱
     */
    private Boolean useEmail;
    /**
     * 创建时间
     */
    private Date createTime;
}