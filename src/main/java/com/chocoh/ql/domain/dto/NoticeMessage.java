package com.chocoh.ql.domain.dto;

import com.chocoh.ql.common.enums.system.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chocoh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMessage {
    private EventTypeEnum eventType;
    private String noticeContent;
    private Long repositoryId;
}
