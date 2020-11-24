package com.snowbuffer.study.java.springboot.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:  mvc配置
 *
 * @author cjb
 * @since 2020-09-20 00:59
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
//        registry.addViewController("/index.html").setViewName("login"); // 这里也可以设置默认视图,功能同DefaultViewController
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new AuthHandler())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/", "/index.html", "/index", "/error");
    }

    public static class AuthHandler extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Object loginUserId = request.getSession().getAttribute("loginUserId");
            if (loginUserId == null) {
                System.out.println("未登录");
                request.getRequestDispatcher("/index.html").forward(request, response);
                return false;
            }
            return true;
        }

    }
}
