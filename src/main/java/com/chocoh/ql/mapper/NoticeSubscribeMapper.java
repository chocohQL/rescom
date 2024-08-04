package com.chocoh.ql.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.common.enums.system.EventTypeEnum;
import com.chocoh.ql.domain.entity.NoticeSubscribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeSubscribeMapper extends BaseMapper<NoticeSubscribe> {
    default List<NoticeSubscribe> selectRepositoryEventSubscriber(Long repositoryId, EventTypeEnum eventType) {
        return this.selectList(new LambdaQueryWrapper<NoticeSubscribe>()
                .eq(NoticeSubscribe::getRepositoryId, repositoryId)
                .eq(NoticeSubscribe::getEventType, eventType));
    }
}




