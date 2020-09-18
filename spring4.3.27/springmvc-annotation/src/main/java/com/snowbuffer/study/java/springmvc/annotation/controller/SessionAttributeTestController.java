package com.snowbuffer.study.java.springmvc.annotation.controller;

import com.snowbuffer.study.java.springmvc.annotation.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 01:42
 */
@Controller
@SessionAttributes(value = {"user"}) // 从map中获取同名的user属性，并存入session， 只对当前Controller范围有效
public class SessionAttributeTestController {

    @RequestMapping("/testSessionAttributes")
    public String test2SessionAttributes(Map<String, Object> map) {
        User user = new User("Tom", 15, "123456");
        map.put("user", user);
        map.put("school", "ccc");
        return "success";
    }

    @RequestMapping("/testGetUserFromSessionAttributes")
    public String testGetUser(HttpSession session) {
        Object user = session.getAttribute("user");
        System.out.println(user);
        return "success";
    }
}
