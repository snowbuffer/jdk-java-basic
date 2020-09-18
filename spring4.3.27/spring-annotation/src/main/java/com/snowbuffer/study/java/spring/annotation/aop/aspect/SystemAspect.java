package com.snowbuffer.study.java.spring.annotation.aop.aspect;

import com.snowbuffer.study.java.spring.annotation.aop.annotation.SwitchUser;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:22
 */
@Aspect
@Order(3)  // 值越大，优先级越小
public class SystemAspect {

    @Pointcut("within(com.snowbuffer.study.java.spring.annotation.aop.service..*)")
    public void withtest() {
    }

    @Pointcut("@annotation(com.snowbuffer.study.java.spring.annotation.aop.annotation.SwitchUser)")
    // 目标方法上标注了@SwitchUser
    public void anno() {
    }

    @Pointcut("withtest() && anno()") // 组合多个pointcut
    public void combine() {
        System.out.println("=========+++"); // 不会执行
    }

    @Pointcut("this(java.io.Serializable)") // 当前proxy需要实现Serializable接口
    public void serialProxy() {
    }

    @Pointcut("target(java.io.Serializable)") // 当前目标对象需要实现Serializable接口
    public void targetObject() {
    }

    @Pointcut("@target(com.snowbuffer.study.java.spring.annotation.aop.annotation.SwitchUser)") // 当前目标对象标注了@SwitchUser2
    public void annoTarget() {
    }

    @Pointcut("args(java.io.Serializable)") // 当前目标方法参数实现了Serializable
    public void argsConfig() {
    }

    @Before(value = "combine() && @annotation(switchUser)") // 通过combine进行切面，并获取注解的switchUser
    @Order(5) // 单独定义无效，只能在DeclaringClass标注才行
    public void before(org.aspectj.lang.JoinPoint joinPoint, SwitchUser switchUser) {
        System.out.println(joinPoint.getClass());
        System.out.println("SystemArchitecture before execute..." + switchUser.value());
    }

    @Before(value = "serialProxy()")
    @Order(4)
    public void before2() {
        System.out.println("SystemArchitecture before2 execute...");
    }

    @Before(value = "targetObject()")
    @Order(3)
    public void before3() {
        System.out.println("SystemArchitecture before3 execute...");
    }

    @Before(value = "annoTarget()")
    @Order(2)
    public void before4() {
        System.out.println("SystemArchitecture before4 execute...");
    }

    @Before(value = "argsConfig()")
    @Order(1)
    public void before5() {
        System.out.println("SystemArchitecture before5 execute...");
    }
}
