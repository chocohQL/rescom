package com.chocoh.ql.exception.repository;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class RepositoryNotFoundException extends BusinessException {
    public RepositoryNotFoundException() {
        super(ResultCodeEnum.REPOSITORY_NOT_FOUND);
    }
}
