package com.snowbuffer.study.java.common.annotation.domain;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 23:42
 */
@RecordTraces({
        @RecordTrace(value = "ManageUser1"),
        @RecordTrace(value = "ManageUser11")
})
@RecordTrace(value = "ManageUser111")
public class ManageUser1 extends BaseUser1 {
}
