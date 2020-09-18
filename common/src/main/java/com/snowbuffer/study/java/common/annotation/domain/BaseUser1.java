package com.snowbuffer.study.java.common.annotation.domain;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 23:42
 */
@RecordTraces({
        @RecordTrace(value = "BaseUser1"),
        @RecordTrace(value = "BaseUser11")
})
public class BaseUser1 {
}
