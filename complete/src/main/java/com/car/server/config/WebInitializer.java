package com.car.server.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by AereXu on 2016/4/24.
 */
public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebConfig.class, JPAConfig.class);
        rootContext.setServletContext(container);

        // Manage the lifecycle of the root application context
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(rootContext));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }

}
