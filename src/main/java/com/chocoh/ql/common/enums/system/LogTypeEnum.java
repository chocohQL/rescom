package com.chocoh.ql.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志类型
 *
 * <p>某个日志类型对应的日志级别高于仓库的日志级别，系统则会记录（0为最高日志级别）</p>
 *
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum LogTypeEnum {
    /**
     * 仓库日志
     */
    REPOSITORY_LOG("repository_log", LogLevelEnum.ALL),
    /**
     * 文件日志
     */
    FILE_log("file_log", LogLevelEnum.SIMPLE);

    @JsonValue
    @EnumValue
    private final String type;

    private final LogLevelEnum logLevel;
}
