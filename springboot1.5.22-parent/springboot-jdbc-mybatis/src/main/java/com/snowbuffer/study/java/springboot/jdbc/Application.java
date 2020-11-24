package com.snowbuffer.study.java.springboot.jdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:  springboot jdbc 测试
 *
 * @author cjb
 * @since 2020-09-19 09:41
 */
@MapperScan(basePackages = {"com.snowbuffer.study.java.springboot.jdbc.mapper"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
