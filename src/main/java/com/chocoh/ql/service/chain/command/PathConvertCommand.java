package com.chocoh.ql.service.chain.command;

import com.chocoh.ql.service.chain.context.FileAccessContext;
import com.chocoh.ql.utils.FilePathUtil;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

/**
 * 访问路径校验
 * @author chocoh
 */
@Service
public class PathConvertCommand implements Command {
    @Override
    public boolean execute(Context context) {
        FileAccessContext param = (FileAccessContext) context;
        String filePath = param.getFilePath();
        String pathAndName = param.getPathAndName();
        if (filePath != null) {
            param.setFilePath(FilePathUtil.removeDuplicateSlashes(filePath));
        }
        if (pathAndName != null) {
            param.setPathAndName(FilePathUtil.removeDuplicateSlashes(pathAndName));
        }
        return false;
    }
}
