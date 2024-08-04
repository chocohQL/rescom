package com.chocoh.ql.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * <p>不同的操作类型记录为不同的日志类型</p>
 *
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum EventTypeEnum {
    FILE_CREATE("create file", LogTypeEnum.FILE_log),
    FILE_DELETE("delete file", LogTypeEnum.FILE_log),
    FILE_UPDATE("update file", LogTypeEnum.FILE_log),

    REPOSITORY_RELEASE("release repository", LogTypeEnum.REPOSITORY_LOG),
    REPOSITORY_SETTING("setting repository", LogTypeEnum.REPOSITORY_LOG),
    REPOSITORY_ROLE_APPLY("apply repository role", LogTypeEnum.REPOSITORY_LOG),
    REPOSITORY_ROLE_CHANGE("change repository role", LogTypeEnum.REPOSITORY_LOG);

    @JsonValue
    @EnumValue
    private final String type;

    private final LogTypeEnum logType;
}
