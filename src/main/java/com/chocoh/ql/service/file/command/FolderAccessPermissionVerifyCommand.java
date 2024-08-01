package com.chocoh.ql.service.file.command;

import com.chocoh.ql.common.enums.AccessLevelEnum;
import com.chocoh.ql.common.enums.AccessRoleEnum;
import com.chocoh.ql.common.enums.FileAccessTypeEnum;
import com.chocoh.ql.domain.entity.FileDo;
import com.chocoh.ql.domain.entity.RepositoryMember;
import com.chocoh.ql.exception.repository.FolderForbiddenException;
import com.chocoh.ql.mapper.FileMapper;
import com.chocoh.ql.service.file.context.FileAccessContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件夹访问校验
 *
 * @author chocoh
 */
@Service
public class FolderAccessPermissionVerifyCommand implements Command {
    @Resource
    private FileMapper fileMapper;

    @Override
    public boolean execute(Context context) {
        FileAccessContext param = (FileAccessContext) context;
        FileDo folder = fileMapper.selectFolder(param.getRepositoryId(), param.getFilePath());
        FileAccessTypeEnum accessType = param.getAccessType();
        RepositoryMember member = param.getMember();
        if (folder != null && folder.getAccessLevel() == AccessLevelEnum.PUBLIC) {
            // 公开文件夹：所有人可，非仓库成员 \ observer 不可写
            if (accessType == FileAccessTypeEnum.READ ||
                    (member != null && member.getRole() != AccessRoleEnum.OBSERVER)) {
                return false;
            }
        } else {
            // 私有文件夹：非仓库成员拒绝，其他成员 developer \ observer 级别只读
            if (member != null && folder != null && member.getUserId().equals(folder.getUserId())) {
                return false;
            }
            if (member != null && !(accessType == FileAccessTypeEnum.READ &&
                        (member.getRole() == AccessRoleEnum.DEVELOPER || member.getRole() == AccessRoleEnum.OBSERVER))) {
                return false;
            }
        }
        // 文件夹权限不足
        throw new FolderForbiddenException();
    }
}