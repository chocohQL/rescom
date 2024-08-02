package com.chocoh.ql.service.chain.command;

import cn.dev33.satoken.stp.StpUtil;
import com.chocoh.ql.common.enums.AccessLevelEnum;
import com.chocoh.ql.common.enums.AccessRoleEnum;
import com.chocoh.ql.common.enums.FileAccessTypeEnum;
import com.chocoh.ql.domain.entity.Repository;
import com.chocoh.ql.domain.entity.RepositoryMember;
import com.chocoh.ql.exception.repository.RepositoryForbiddenException;
import com.chocoh.ql.exception.repository.RepositoryNotFoundException;
import com.chocoh.ql.mapper.RepositoryMapper;
import com.chocoh.ql.mapper.RepositoryMemberMapper;
import com.chocoh.ql.service.chain.context.FileAccessContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 仓库访问校验
 * @author chocoh
 */
@Service
public class RepoAccessPermissionVerifyCommand implements Command {
    @Resource
    private RepositoryMapper repositoryMapper;
    @Resource
    private RepositoryMemberMapper repositoryMemberMapper;

    @Override
    public boolean execute(Context context) {
        FileAccessContext param = (FileAccessContext) context;
        Long repositoryId = param.getRepositoryId();
        Repository repository = repositoryMapper.selectById(repositoryId);
        if (repository == null) {
            throw new RepositoryNotFoundException();
        }
        FileAccessTypeEnum accessType = param.getAccessType();
        RepositoryMember member = repositoryMemberMapper.selectRepositoryUser(repositoryId, StpUtil.getLoginIdAsLong());
        param.setMember(member);
        if (member == null) {
            // 非仓库成员，校验访问级别
            if (repository.getAccessLevel() == AccessLevelEnum.PUBLIC && accessType == FileAccessTypeEnum.READ) {
                return false;
            }
        } else {
            // 是仓库管理，校验角色权限
            if (member.getRole() != AccessRoleEnum.OBSERVER || accessType == FileAccessTypeEnum.READ) {
                return false;
            }
        }
        // 仓库权限不足
        throw new RepositoryForbiddenException();
    }
}
