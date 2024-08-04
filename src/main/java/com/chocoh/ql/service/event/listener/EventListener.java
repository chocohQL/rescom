package com.chocoh.ql.service.event.listener;

import com.chocoh.ql.domain.context.EventContext;

/**
 * @author chocoh
 */
public interface EventListener {
    void doEvent(EventContext context);
}
