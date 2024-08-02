package com.chocoh.ql.service.chain.command;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import com.chocoh.ql.common.enums.FileTypeEnum;
import com.chocoh.ql.domain.file.FileAccessRequest;
import com.chocoh.ql.domain.vo.FileInfo;
import com.chocoh.ql.service.chain.context.FileResultContext;
import com.chocoh.ql.utils.NaturalOrderComparator;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件排序
 * - 文件夹始终比文件排序高
 * - 默认按照名称排序
 * - 默认排序为升序
 * - 按名称排序不区分大小写
 *
 * @author chocoh
 */
@Service
public class FileSortCommand implements Command {
    @Override
    public boolean execute(Context context) {
        FileResultContext param = (FileResultContext) context;
        FileAccessRequest request = param.getFileAccessRequest();

        List<FileInfo> fileInfos = param.getFileInfos();
        if (CollUtil.isEmpty(fileInfos) || request.getOrderBy() == null || request.getOrderDirection() == null) {
            return false;
        }

        request.setDefaultValue();
        String orderBy = request.getOrderBy();
        String orderDirection = request.getOrderDirection();

        fileInfos.sort((file1, file2) -> {
            FileTypeEnum file1Type = file1.getType();
            FileTypeEnum file2Type = file2.getType();
            if (file1Type.equals(file2Type)) {
                int result;
                if (orderBy.equals("time")) {
                    result = CompareUtil.compare(file1.getCreateTime(), file2.getCreateTime());
                } else if (orderBy.equals("size")) {
                    result = CompareUtil.compare(file1.getFileSize(), file2.getFileSize());
                } else {
                    NaturalOrderComparator naturalOrderComparator = new NaturalOrderComparator();
                    result = naturalOrderComparator.compare(file1.getFileName(), file2.getFileName());
                }
                return "asc".equals(orderDirection) ? result : -result;
            }
            if (file1Type.equals(FileTypeEnum.FOLDER)) {
                return -1;
            } else {
                return 1;
            }
        });

        return false;
    }
}
