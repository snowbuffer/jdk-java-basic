package com.snowbuffer.study.java.springmvc.annotation.controller;

import com.snowbuffer.study.java.springmvc.annotation.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 00:25
 */
@Controller
public class CommonTestController {

    @RequestMapping(value = "/testMethod", method = RequestMethod.POST)
    public String testMethod() {
        System.out.println("testMethod");
        return "success";
    }

    @RequestMapping(value = "testParamsAndHeaders", params = {"username",
            "age!=10"}, headers = {"Accept-Language=en-US,zh;q=0.8"})  // 所有条件必须满足才能执行
    public String testParamsAndHeaders() {
        System.out.println("testParamsAndHeaders");
        return "success";
    }

    @RequestMapping("/testAntPath/*/abc")
    public String testAntPath() {
        System.out.println("testAntPath");
        return "success";
    }

    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable("id") Integer id, @PathVariable("id2") Integer id2) {
        System.out.println("testPathVariable: " + id);
        return "success";
    }


    @RequestMapping(value = "/testRest", method = RequestMethod.POST)
    public String testRest() {
        System.out.println("testRest POST");
        return "success";
    }

    // POST http://localhost:8080/testRest/11?_method=DELETE
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id) {
        System.out.println("testRest Delete: " + id);
        return "success";
    }

    // POST http://localhost:8080/testRest/11?_method=PUT
    @RequestMapping(value = "/testRest/{id}", method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id) {
        System.out.println("testRest Put: " + id);
        return "success";
    }

    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(
            @RequestParam(value = "username") String un,
            @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        System.out.println("testRequestParam, username: " + un + ", age: "
                + age);
        return "success";
    }

    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(
            @RequestHeader(value = "Accept-Language") String al) {
        System.out.println("testRequestHeader, Accept-Language: " + al);
        return "success";
    }

    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
        System.out.println("testCookieValue: sessionId: " + sessionId);
        return "success";
    }

    @RequestMapping("/testPojo")
    public String testPojo(User user) {
        System.out.println("testPojo: " + user);
        return "success";
    }

    @RequestMapping("/testServletAPI")
    public void testServletAPI(HttpServletRequest request,
                               HttpServletResponse response, Writer out) throws IOException {
        System.out.println("testServletAPI, " + request + ", " + response);
        out.write("hello springmvc");
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        String viewName = "success";
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("time", new Date());
        return modelAndView;
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map) {
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
        return "success";
    }
}
