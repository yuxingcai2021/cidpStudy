package com.bmw.seed.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 将拦截器注册到spring中
 */
@Configuration
public class LoginAdapter implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");//表示拦截所有的请求
    }
}
