package com.chocoh.ql.domain.context;

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
public class EventContext {
    private EventTypeEnum eventType;
    private Long userId;
    private Long repositoryId;
    private Long fileId;
    private String message;
}
