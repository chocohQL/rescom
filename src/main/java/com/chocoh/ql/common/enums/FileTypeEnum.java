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
public enum FileTypeEnum {
    /**
     * 文件夹
     */
    FOLDER(0),
    /**
     * 文件
     */
    FILE(1);

    @EnumValue
    @JsonValue
    private final int value;
}
