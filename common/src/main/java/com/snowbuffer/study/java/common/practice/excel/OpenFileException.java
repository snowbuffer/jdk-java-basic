package com.snowbuffer.study.java.common.practice.excel;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-14 22:57
 */
public class OpenFileException extends RuntimeException {

    public OpenFileException() {
        super();
    }

    public OpenFileException(String message) {
        super(message);
    }

    public OpenFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenFileException(Throwable cause) {
        super(cause);
    }

    protected OpenFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
