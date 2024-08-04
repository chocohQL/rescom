package com.chocoh.ql.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件访问类型
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum FileAccessTypeEnum {
    /**
     * 读
     */
    READ("read"),
    /**
     * 写
     */
    WRITE("write"),
    /**
     * 删除
     */
    DELETE("delete"),
    /**
     * 创建
     */
    CREATE("create");

    private final String type;
}
