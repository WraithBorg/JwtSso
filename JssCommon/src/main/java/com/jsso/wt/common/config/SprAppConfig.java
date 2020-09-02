package com.jsso.wt.common.config;

import com.jsso.wt.common.interceptor.SprLoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SprAppConfig extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfigurationSupport.class);

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SprLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/Login/loginPage",
                        "/Login/loginByUserName",
                        "/Abc/showAbcNoPass");

        super.addInterceptors(registry);
    }
}
