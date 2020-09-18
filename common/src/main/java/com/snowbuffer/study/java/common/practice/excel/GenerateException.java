package com.snowbuffer.study.java.common.practice.excel;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-14 23:00
 */
public class GenerateException extends RuntimeException {

    public GenerateException() {
        super();
    }

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateException(Throwable cause) {
        super(cause);
    }

    protected GenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
