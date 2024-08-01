package com.chocoh.ql.exception.file;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FileNotFoundException extends BusinessException {
    public FileNotFoundException() {
        super(ResultCodeEnum.FILE_NOT_FOUND);
    }
}
