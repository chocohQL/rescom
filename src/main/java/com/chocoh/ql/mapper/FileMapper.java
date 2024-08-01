package com.chocoh.ql.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.common.enums.FileTypeEnum;
import com.chocoh.ql.domain.entity.FileDo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<FileDo> {
    default FileDo selectFolder(Long repositoryId, String path) {
        return selectOne(new LambdaQueryWrapper<FileDo>()
                .select(FileDo::getId)
                .eq(FileDo::getFilePath, path)
                .eq(FileDo::getRepositoryId, repositoryId)
                .eq(FileDo::getType, FileTypeEnum.FOLDER));
    }

    default List<FileDo> selectFileList(Long repositoryId, String path) {
        return selectList(new LambdaQueryWrapper<FileDo>()
                .eq(FileDo::getFilePath, path)
                .eq(FileDo::getRepositoryId, repositoryId));
    }
}




