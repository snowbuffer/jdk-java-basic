package com.snowbuffer.study.java.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-07 23:17
 */
public class Log4jTest {

    public static void main(String[] args) {
        Log log = LogFactory.getLog(Log4jTest.class);
        log.info("start...");
        log.warn("end.");
    }
}
