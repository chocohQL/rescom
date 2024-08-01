package com.chocoh.ql.exception.file;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FileMd5CalculationErrorException extends BusinessException {
    public FileMd5CalculationErrorException() {
        super(ResultCodeEnum.FILE_MD5_CALCULATION_ERROR_EXCEPTION);
    }
}
