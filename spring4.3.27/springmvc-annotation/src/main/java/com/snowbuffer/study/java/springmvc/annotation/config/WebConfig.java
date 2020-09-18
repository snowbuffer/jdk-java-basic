package com.snowbuffer.study.java.springmvc.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-23 00:39
 */
@Configuration
@ComponentScan(value = "com.snowbuffer.study.java.springmvc.annotation",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})},
        useDefaultFilters = false)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    //视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    //静态资源访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                System.out.println(locale);
                return new SimpleDateFormat("yyyy-MM-dd").parse(text);
            }

            @Override
            public String print(Date object, Locale locale) {
                System.out.println(object);
                return "aaaa";
            }
        });
    }

//    //拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // TODO Auto-generated method stub
//        //super.addInterceptors(registry);
//        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
//    }
}
