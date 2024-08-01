package com.chocoh.ql.service.file.command;

import cn.hutool.core.collection.CollUtil;
import com.chocoh.ql.domain.vo.FileInfo;
import com.chocoh.ql.service.file.context.FileResultContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理文件隐藏
 * @author chocoh
 */
@Service
public class FileHiddenCommand implements Command {
    @Override
    public boolean execute(Context context) {
        FileResultContext param = (FileResultContext) context;
        List<FileInfo> fileInfos = param.getFileInfos();

        if (CollUtil.isEmpty(fileInfos)) {
            return false;
        }

        List<FileInfo> result = fileInfos.stream()
                .filter(i -> !i.getHidden())
                .collect(Collectors.toList());

        param.setFileInfos(result);
        return false;
    }
}