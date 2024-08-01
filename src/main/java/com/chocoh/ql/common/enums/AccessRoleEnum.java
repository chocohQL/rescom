package com.chocoh.ql.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum AccessRoleEnum {
    /**
     * 所有者
     */
    OWNER("owner"),
    /**
     * 管理员
     */
    ADMINISTRATOR("administrator"),
    /**
     * 开发者
     */
    DEVELOPER("developer"),
    /**
     * 观察者
     */
    OBSERVER("observer");

    @EnumValue
    @JsonValue
    private final String role;

}
