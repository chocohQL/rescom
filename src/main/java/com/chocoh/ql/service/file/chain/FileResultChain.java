package com.chocoh.ql.service.file.chain;

import com.chocoh.ql.domain.file.FileAccessRequest;
import com.chocoh.ql.domain.vo.FileInfo;
import com.chocoh.ql.service.file.command.*;
import com.chocoh.ql.service.file.context.FileResultContext;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author chocoh
 */
@Service
public class FileResultChain extends ChainBase {
    @Resource
    private FileHiddenCommand fileHiddenCommand;
    @Resource
    private FileSortCommand fileSortCommand;

    @PostConstruct
    public void init() {
        this.addCommand(fileHiddenCommand);
        this.addCommand(fileSortCommand);
    }

    public FileResultContext execute(FileResultContext context) throws Exception {
        super.execute(context);
        return context;
    }

    public FileResultContext execute(List<FileInfo> fileInfos, FileAccessRequest fileAccessRequest) throws Exception {
        FileResultContext context = FileResultContext.builder()
                .fileInfos(fileInfos)
                .fileAccessRequest(fileAccessRequest)
                .build();
        super.execute(context);
        return context;
    }
}
