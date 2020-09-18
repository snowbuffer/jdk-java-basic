package com.snowbuffer.study.java.common.log;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-07 23:34
 */
public class Log4j2Test {

    public static void main(String[] args) {
        Logger logger4j2 = LogManager.getLogger(Log4j2Test.class);
        logger4j2.info("logger4j2 start...");
        logger4j2.warn("logger4j2 end...");

        // slf4j 代理 log4j2
        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Log4j2Test.class);
        slf4jLogger.info("slf4jLogger start...");
        slf4jLogger.warn("slf4jLogger end...");

    }
}
