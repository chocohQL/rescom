package com.chocoh.ql.service.event.listener;

import com.chocoh.ql.common.enums.system.LogTypeEnum;
import com.chocoh.ql.domain.context.EventContext;
import com.chocoh.ql.domain.entity.FileLog;
import com.chocoh.ql.mapper.FileLogMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chocoh
 */
@Getter
@Service
public class BusinessLogEventListener implements EventListener{
    @Resource
    private FileLogMapper fileLogMapper;

    @Override
    public void doEvent(EventContext context) {
        if (context.getEventType().getLogType() == LogTypeEnum.FILE_log) {
            FileLog fileLog = FileLog.builder()
                    .eventType(context.getEventType())
                    .userId(context.getUserId())
                    .fileId(context.getFileId())
                    .repositoryId(context.getRepositoryId())
                    .remark(context.getMessage())
                    .build();
            fileLogMapper.insert(fileLog);
        }
    }
}
