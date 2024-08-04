package com.chocoh.ql.service.event.listener;

import com.chocoh.ql.domain.context.EventContext;
import com.chocoh.ql.domain.dto.NoticeMessage;
import com.chocoh.ql.utils.MessageTemplate;
import lombok.Getter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chocoh
 */
@Getter
@Service
public class BusinessNoticeEventListener implements EventListener {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void doEvent(EventContext context) {
        // TODO
        NoticeMessage noticeMessage = NoticeMessage.builder()
                .repositoryId(context.getRepositoryId())
                .eventType(context.getEventType())
                .noticeContent(MessageTemplate.getSubscribeNoticeContent())
                .build();
        rabbitTemplate.convertAndSend(
                "rescom.topic.notice",
                "business.#",
                noticeMessage
        );
    }
}
