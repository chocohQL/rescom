package com.chocoh.ql.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.domain.entity.RepositoryMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepositoryMemberMapper extends BaseMapper<RepositoryMember> {
    default RepositoryMember selectRepositoryUser(Long repositoryId, Long userId) {
        return this.selectOne(new LambdaQueryWrapper<RepositoryMember>()
                .eq(RepositoryMember::getRepositoryId, repositoryId)
                .eq(RepositoryMember::getUserId, userId));
    }
}




