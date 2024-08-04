package com.chocoh.ql.listener;

import cn.hutool.core.collection.CollUtil;
import com.chocoh.ql.common.enums.system.NoticeTypeEnum;
import com.chocoh.ql.domain.dto.NoticeMessage;
import com.chocoh.ql.domain.entity.Notice;
import com.chocoh.ql.domain.entity.NoticeSubscribe;
import com.chocoh.ql.mapper.NoticeMapper;
import com.chocoh.ql.mapper.NoticeSubscribeMapper;
import com.chocoh.ql.mapper.UserMapper;
import com.chocoh.ql.utils.EmailClient;
import com.chocoh.ql.utils.MessageTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chocoh.ql.common.constant.Constants.SUBSCRIBE_EMAIL_SUBJECT;

/**
 * @author chocoh
 */
@Component
public class SendNoticeListener {
    @Resource
    private EmailClient emailClient;
    @Resource
    private NoticeSubscribeMapper noticeSubscribeMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserMapper userMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("rescom.notice.business"),
            exchange = @Exchange(name = "rescom.topic.notice", type = ExchangeTypes.TOPIC),
            key = "business.#"
    ))
    public void sendBusinessNotice(NoticeMessage noticeMessage) {
        List<NoticeSubscribe> subscribes = noticeSubscribeMapper
                .selectRepositoryEventSubscriber(noticeMessage.getRepositoryId(), noticeMessage.getEventType());

        subscribes.forEach(i -> noticeMapper.insert(
                        Notice.builder()
                                .type(NoticeTypeEnum.SUBSCRIBE)
                                .content(noticeMessage.getNoticeContent())
                                .userId(i.getUserId())
                                .build()
                )
        );

        List<String> emails = userMapper.selectEmails(
                subscribes.stream()
                        .filter(NoticeSubscribe::getUseEmail)
                        .map(NoticeSubscribe::getUserId)
                        .collect(Collectors.toList())
        );

        if (CollUtil.isNotEmpty(emails)) {
            emailClient.sendHttpEmails(
                    new ArrayList<>(emails),
                    SUBSCRIBE_EMAIL_SUBJECT,
                    noticeMessage.getNoticeContent()
            );
        }
    }
}
