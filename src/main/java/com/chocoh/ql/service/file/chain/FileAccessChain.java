package com.chocoh.ql.service.file.chain;

import com.chocoh.ql.common.enums.FileAccessTypeEnum;
import com.chocoh.ql.service.file.command.*;
import com.chocoh.ql.service.file.context.FileAccessContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author chocoh
 */
@Slf4j
@Service
public class FileAccessChain extends ChainBase {
    @Resource
    private PathConvertCommand pathConvertCommand;
    @Resource
    private FolderAccessPermissionVerifyCommand folderAccessPermissionVerifyCommand;
    @Resource
    private RepoAccessPermissionVerifyCommand repoAccessPermissionVerifyCommand;

    @PostConstruct
    public void init() {
        this.addCommand(pathConvertCommand);
        this.addCommand(repoAccessPermissionVerifyCommand);
        this.addCommand(folderAccessPermissionVerifyCommand);
    }

    public FileAccessContext execute(FileAccessContext context) throws Exception {
        super.execute(context);
        return context;
    }

    public FileAccessContext execute(FileAccessTypeEnum accessType, Long repositoryId, String filePath, String fileName) throws Exception {
        FileAccessContext context = FileAccessContext.builder()
                .accessType(accessType)
                .repositoryId(repositoryId)
                .filePath(filePath)
                .fileName(fileName)
                .build();
        super.execute(context);
        return context;
    }

    public FileAccessContext execute(FileAccessTypeEnum accessType, Long repositoryId, String pathAndName) throws Exception {
        FileAccessContext context = FileAccessContext.builder()
                .accessType(accessType)
                .repositoryId(repositoryId)
                .pathAndName(pathAndName)
                .build();
        super.execute(context);
        return context;
    }
}
