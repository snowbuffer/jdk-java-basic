package com.snowbuffer.study.java.springmvc.annotation.controller;

import com.snowbuffer.study.java.springmvc.annotation.domain.User;
import com.snowbuffer.study.java.springmvc.annotation.domain.ValidateUser;
import com.snowbuffer.study.java.springmvc.annotation.service.AddressService;
import com.snowbuffer.study.java.springmvc.annotation.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-23 00:44
 */
@Controller
public class UserController implements ApplicationContextAware {

    @ResponseBody
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello() {
        return "hello";
    }

    @RequestMapping("/success")
    public String success(HttpServletRequest request, Integer aaa, Integer bbb) {
        System.out.println(request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE));

        RequestAttributes requestAttributes =
                RequestContextHolder.getRequestAttributes();

        requestAttributes.registerDestructionCallback("aa", new Runnable() {
            @Override
            public void run() {
                System.out.println("==完成");
            }
        }, RequestAttributes.SCOPE_REQUEST);
        return "success";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        UserService userService = applicationContext.getBean(UserService.class);
        UserController userController = applicationContext.getBean(UserController.class);
//        AddressService addressService = applicationContext.getBean(AddressService.class);
//        System.out.println("addressService:" + addressService);
        System.out.println("userService:" + userService);
        System.out.println("userController:" + userController);
    }

    // 自定义date类型转换器
    @ResponseBody
    @RequestMapping(value = "/userForm")
    public User form(User user) {
        System.out.println(user);
        return user;
    }

    // 自定义date类型转换器
    @ResponseBody
    @RequestMapping(value = "/userForm2")
    public ValidateUser form2(@Valid ValidateUser user, BindingResult result) {
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                System.out.println(fieldError);
            }
        }
        System.out.println(user);
        return user;
    }

    // 自定义date类型转换器
    @ResponseBody
    @RequestMapping(value = "/userForm3")
    public ValidateUser form3(@RequestBody ValidateUser user) {
        System.out.println(user);
        return user;
    }


}
