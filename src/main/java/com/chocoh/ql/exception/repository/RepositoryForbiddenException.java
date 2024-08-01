package com.chocoh.ql.exception.repository;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class RepositoryForbiddenException extends BusinessException {
    public RepositoryForbiddenException() {
        super(ResultCodeEnum.REPOSITORY_FORBIDDEN);
    }
}
