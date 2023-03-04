package com.example.last.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //addResourceHandler是指你想在url请求的路径
        //addResourceLocations是图片存放的真实路径
        String path= System.getProperty("user.dir");
        registry.addResourceHandler("/**").addResourceLocations("file:"+path+"/upload").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}