package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户社区关系表
 */
@Data
@TableName("r_user_community")
public class UserCommunity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 社区id
     */
    private Object communityId;

    /**
     *
     */
    private Date createTime;
}