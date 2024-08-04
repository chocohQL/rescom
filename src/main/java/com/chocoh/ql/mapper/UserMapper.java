package com.chocoh.ql.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chocoh.ql.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chocoh
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    default List<String> selectEmails(List<Long> ids) {
        return this.selectList(new LambdaQueryWrapper<User>()
                        .select(User::getEmail)
                        .in(User::getId, ids)
                ).stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }
}
