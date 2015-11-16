package com.mt.configs;

import com.mt.controllers.MultiTenancyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by bharathim on 11/11/2015.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    HandlerInterceptor multiTenancyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(multiTenancyInterceptor);
        //registry.addInterceptor(new MultiTenancyInterceptor());//this did not work even when @Bean was defined in DatabaseConfig. entity Manager was not available in MultiTenancyInterceptor
    }
}