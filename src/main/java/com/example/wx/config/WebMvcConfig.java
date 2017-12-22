package com.example.wx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 寇含尧 on 2017/10/24.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 视图解析，用来映射路径和实际页面的位置
     *
     * @return
     */
    /*@Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/templates/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        http://www.cnblogs.com/vicis/articles/6077839.html
        registry.addViewController("/").setViewName("login");
        /*registry.addViewController("/home").setViewName("myhome");
        registry.addViewController("/hello").setViewName("helloworld");
        registry.addRedirectViewController("/home", "/hello");
        registry.addStatusController("/detail", HttpStatus.BAD_REQUEST);*/
    }
}
