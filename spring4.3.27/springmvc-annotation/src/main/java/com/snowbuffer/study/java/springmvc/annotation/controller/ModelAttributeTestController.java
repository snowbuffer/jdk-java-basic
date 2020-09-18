package com.snowbuffer.study.java.springmvc.annotation.controller;

import com.snowbuffer.study.java.springmvc.annotation.domain.User;
import com.snowbuffer.study.java.springmvc.annotation.domain.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 09:38
 */
@Controller
public class ModelAttributeTestController {

    // 这是一个ModelMetod
    @ModelAttribute  // 只对当前Controller范围有效
    public User findUser(Integer id) {   // 每一个简单属性上都有一个隐藏的@RequestParam(required=false)
        System.out.println("查询id:" + id);
        User user = new User("aa", id, "bbb");
        return user; // 以User作为user变量，存放进Model中
    }

    // 这是一个HandlerMethod
    @RequestMapping("/testModelAttributeUpdateUser")
    public String updateUser(User user111) { // 以User作为user变量，从Model中取出user实例  每个自定义类型上都有一个 @ModelAtrribute("自定义类型小写")
        user111.setAge(200);
        System.out.println(user111);
        return "success";
    }

    // 先调用同类中的ModelMethod,再调用HandlerMethod
    // http://localhost:8080/testModelAttributeUpdateUser?age=1&id=111
    // -> 先执行findUser  -> print: 查询id:111
    // -> 再执行updateUser -> print: User(userName=aa, age=200, remark=bbb)

    // 流程：详见：org.springframework.web.method.annotation.ModelAttributeMethodProcessor.resolveArgument
    //  1. 解析findUser#id(@RequestParam(required=false)) 中间涉及类型转换：string -> Integer
    //  2. 将findUser返回结果存在ModelAndViewContainer(每次请求都是新建的实例Container)
    //  3. 开始执行updateUser方法
    // 3.1 解析updateUser#user111变量名，并使用变量user从第二步中的ModelAndViewContainer取出相应的User实例(findUser返回结果)
    // 3.2 将取出的User实例对象与当前Request中的属性进行合并
    // 3.3 使用合并后的User实例对象进行convert转换成updateUser中的User user111对象


}
