package com.wangwenjun.concurrency.custombus;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 15:46
 */
public interface CustomEventExceptionHandler {

    void handle(Throwable cause, CustomEventContext context);
}
