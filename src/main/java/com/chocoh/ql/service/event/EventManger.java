package com.chocoh.ql.service.event;

import com.chocoh.ql.domain.context.EventContext;
import com.chocoh.ql.service.event.listener.EventListener;
import com.chocoh.ql.service.event.listener.BusinessLogEventListener;
import com.chocoh.ql.service.event.listener.BusinessNoticeEventListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chocoh
 */
@Slf4j
@Getter
@Service
public class EventManger {
    private final List<EventListener> listeners = new ArrayList<>();

    @Resource
    private BusinessLogEventListener businessLogEventListener;
    @Resource
    private BusinessNoticeEventListener businessNoticeEventListener;

    @PostConstruct
    private void init() {
        this.subscribe(businessLogEventListener);
        this.subscribe(businessNoticeEventListener);
    }

    public void subscribe(EventListener eventListener) {
        if (eventListener != null) {
            listeners.add(eventListener);
        }
    }

    public void unsubscribe(EventListener eventListener) {
        if (eventListener != null) {
            listeners.remove(eventListener);
        }
    }

    public void push(EventContext message) {
        for (EventListener eventListener : listeners) {
            eventListener.doEvent(message);
        }
    }
}
