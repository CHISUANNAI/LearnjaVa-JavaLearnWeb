package com.example.last.config;

import com.example.last.interceptor.loginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class lastWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new loginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login","/userlogin","/css/**","/fonts/**","/images/**","/js/**"
                        ,"/static/**","/register","/findpwd","/getActive","/admin","/addchoices","/addjudges","/addqa","/loginadmin","/addword");
    }
}
