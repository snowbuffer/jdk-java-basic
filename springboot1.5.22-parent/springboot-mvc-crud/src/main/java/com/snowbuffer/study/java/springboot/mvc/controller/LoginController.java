package com.snowbuffer.study.java.springboot.mvc.controller;

import com.snowbuffer.study.java.springboot.mvc.exception.UserNotExistException;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Description: 登录控制器
 *
 * @author cjb
 * @since 2020-09-21 19:58
 */
@Controller
public class LoginController {

    @PostMapping(value = "/user/login")
    public String login(HttpServletRequest request, Map<String, Object> returnMap) {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if ("aaa".equals(userName)) {
            throw new UserNotExistException("用户不存在");
        }

        boolean isOk = "root".equals(userName) && "root".equals(password);
        if (isOk) {
            request.getSession().setAttribute("loginUserId", true);
            request.getSession().setAttribute("loginUserName", userName);
            System.out.println("登录成功");
            return "redirect:/main";
        }
        System.out.println("登录失败");
        returnMap.put("msg", "登录失败...");
        return "/login";
    }
}
