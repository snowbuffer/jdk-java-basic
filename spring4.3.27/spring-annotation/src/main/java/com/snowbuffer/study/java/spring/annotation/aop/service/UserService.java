package com.snowbuffer.study.java.spring.annotation.aop.service;

import com.snowbuffer.study.java.spring.annotation.aop.annotation.SwitchUser;
import com.snowbuffer.study.java.spring.annotation.aop.annotation.SwitchUser2;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:25
 */
@Service
@SwitchUser2
public class UserService implements Serializable {

    @SwitchUser("111")
    public void test() {
        System.out.println("===11");
    }

    public void test2() {
        System.out.println("===22");
    }

    public void test3() {
        System.out.println("===33");
    }

    public void test4() {
        System.out.println("===44");
    }

    public void test5(Integer i, Integer j) {
        System.out.println("===55");
    }

}