package com.chocoh.ql.exception.repository;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FolderForbiddenException extends BusinessException {
    public FolderForbiddenException() {
        super(ResultCodeEnum.FOLDER_FORBIDDEN);
    }
}
