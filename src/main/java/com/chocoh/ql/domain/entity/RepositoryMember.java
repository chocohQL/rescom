package com.chocoh.ql.domain.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chocoh.ql.common.enums.AccessRoleEnum;
import lombok.Data;

/**
 * 仓库成员关系表
 */
@Data
@TableName("r_repository_member")
public class RepositoryMember implements Serializable {
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
     * 角色
     */
    private AccessRoleEnum role;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}