package com.chocoh.ql.exception;

import lombok.Getter;

/**
 * @author chocoh
 */
@Getter
public class BaseException extends RuntimeException{
    private int code;

    private String defaultMessage;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        this.defaultMessage = message;
    }

    public BaseException(int code, String message) {
        this.code = code;
        this.defaultMessage = message;
    }
}
