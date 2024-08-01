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
public enum AccessLevelEnum {
    /**
     * 公开的
     */
    PUBLIC(0),
    /**
     * 私有的
     */
    PRIVATE(1);

    @JsonValue
    @EnumValue
    private final int level;
}
