package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.system.NoticeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_notice")
public class Notice implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 类型（0:订阅通知 1:系统通知 2:系统公告）
     */
    private NoticeTypeEnum type;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 内容
     */
    private String content;
    /**
     * 0未读 1已读
     */
    private Integer read;
    /**
     * 创建时间
     */
    private Date createTime;
}