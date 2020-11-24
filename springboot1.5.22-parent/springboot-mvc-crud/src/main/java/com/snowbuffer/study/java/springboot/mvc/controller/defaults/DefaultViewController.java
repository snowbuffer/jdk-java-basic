package com.snowbuffer.study.java.springboot.mvc.controller.defaults;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:  默认的view控制器
 *
 * @author cjb
 * @since 2020-09-21 20:00
 */
@Controller
public class DefaultViewController {

    @RequestMapping(value = {"/", "/index.html", "/index"}, method = RequestMethod.GET)
    public String defaultIndexPage(HttpServletRequest request) {
        Object loginUserId = request.getSession().getAttribute("loginUserId");
        if (loginUserId != null) {
            return "redirect:/main";
        }
        return "login";
    }

    @RequestMapping(value = {"/main.html", "/main"}, method = RequestMethod.GET)
    public String defaultMainPage() {
        return "dashboard";
    }
}
