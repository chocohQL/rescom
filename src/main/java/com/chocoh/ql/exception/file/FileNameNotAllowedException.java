package com.chocoh.ql.exception.file;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FileNameNotAllowedException extends BusinessException {
    public FileNameNotAllowedException() {
        super(ResultCodeEnum.FILE_NAME_NOT_ALLOWED);
    }
}
