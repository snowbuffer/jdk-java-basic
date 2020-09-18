package com.snowbuffer.study.java.spring.annotation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-09-03 10:23
 */

@Configuration
public class TestConfig1 {

    @Bean
    CustomPerson customPersonInstance5() {
        System.out.println("===customPersonInstance实例化5===");
        return new CustomPerson();
    }

    @Configuration
    class TTTTTT extends PPPPPP implements GGGGGG {

        @Bean
        CustomPerson customPersonInstance3() {
            System.out.println("===customPersonInstance实例化3===");
            return new CustomPerson();
        }
    }

    interface GGGGGG {

        @Bean
        default CustomPerson customPersonInstance1() {
            System.out.println("===customPersonInstance实例化1===");
            return new CustomPerson();
        }
    }

    class PPPPPP {

        @Bean
        CustomPerson customPersonInstance2() {
            System.out.println("===customPersonInstance实例化2===");
            return new CustomPerson();
        }
    }

    static class CustomPerson {

    }
}
