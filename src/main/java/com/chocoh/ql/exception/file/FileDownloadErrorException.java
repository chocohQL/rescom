package com.chocoh.ql.exception.file;

import com.chocoh.ql.common.enums.ResultCodeEnum;
import com.chocoh.ql.exception.BusinessException;

/**
 * @author chocoh
 */
public class FileDownloadErrorException extends BusinessException {
    public FileDownloadErrorException() {
        super(ResultCodeEnum.FILE_DOWNLOAD_ERROR);
    }
}
