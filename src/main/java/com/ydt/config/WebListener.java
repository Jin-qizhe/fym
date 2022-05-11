package com.ydt.config;

import com.ydt.util.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @date 2020/8/7
 * 将静态变量和方法放到页面，供freemarker调用
 */
@Component
public class WebListener implements ServletContextListener {
    @Autowired
    Dict dict;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute("ts", System.currentTimeMillis());//用于静态文件，防止缓存
        servletContext.setAttribute("dict", dict);
        servletContext.setAttribute("cp", contextPath);
    }

}
