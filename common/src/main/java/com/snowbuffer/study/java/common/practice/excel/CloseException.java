package com.snowbuffer.study.java.common.practice.excel;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-15 00:22
 */
public class CloseException extends RuntimeException {

    public CloseException() {
        super();
    }

    public CloseException(String message) {
        super(message);
    }

    public CloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseException(Throwable cause) {
        super(cause);
    }

    protected CloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
