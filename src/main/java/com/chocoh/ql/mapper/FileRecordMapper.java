package com.chocoh.ql.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chocoh.ql.common.enums.system.FileTypeEnum;
import com.chocoh.ql.domain.entity.FileRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileRecordMapper extends BaseMapper<FileRecord> {
    default FileRecord selectFolder(Long repositoryId, String path) {
        return selectOne(new LambdaQueryWrapper<FileRecord>()
                .select(FileRecord::getId)
                .eq(FileRecord::getFilePath, path)
                .eq(FileRecord::getRepositoryId, repositoryId)
                .eq(FileRecord::getType, FileTypeEnum.FOLDER));
    }

    default List<FileRecord> selectFileList(Long repositoryId, String path) {
        return selectList(new LambdaQueryWrapper<FileRecord>()
                .eq(FileRecord::getFilePath, path)
                .eq(FileRecord::getRepositoryId, repositoryId));
    }
}




