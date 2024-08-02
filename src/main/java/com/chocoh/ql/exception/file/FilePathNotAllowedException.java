package com.chocoh.ql.exception.file;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FilePathNotAllowedException extends BusinessException {
    public FilePathNotAllowedException() {
        super(ResultCodeEnum.FILE_PATH_NOT_ALLOWED);
    }
}
