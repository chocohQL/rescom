package com.chocoh.ql.common.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chocoh
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
    /**
     * 订阅通知
     */
    SUBSCRIBE(0),
    /**
     * 系统通知
     */
    SYSTEM(1),
    /**
     * 系统公告
     */
    NOTIFICATION(0);

    @JsonValue
    @EnumValue
    private final int type;
}
