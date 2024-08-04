package com.chocoh.ql.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志级别
 *
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum LogLevelEnum {
    /**
     * 所有
     */
    ALL(0),
    /**
     * 简易
     */
    SIMPLE(1);

    @JsonValue
    @EnumValue
    private final int logLevel;
}
