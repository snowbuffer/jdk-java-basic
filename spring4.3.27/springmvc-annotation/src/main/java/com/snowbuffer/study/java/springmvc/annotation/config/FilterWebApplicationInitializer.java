package com.snowbuffer.study.java.springmvc.annotation.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-25 01:03
 */
public class FilterWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

//        //注册组件  ServletRegistration -> UserServlet extends HttpServlet
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("userServlet", new UserServlet());
//        //配置servlet的映射信息
//        servlet.addMapping("/user");

//        //注册Listener  -> UserListener implements ServletContextListener
//        servletContext.addListener(UserListener.class);

        // 注册filter -> UserFilter implements Filter
        FilterRegistration.Dynamic filter = servletContext.addFilter("hiddenMethodFilter", HiddenHttpMethodFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}
