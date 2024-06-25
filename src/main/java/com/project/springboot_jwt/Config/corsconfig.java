package com.project.springboot_jwt.Config;

import com.project.springboot_jwt.Interceptor.tokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class corsconfig implements WebMvcConfigurer {
    @Autowired
    private tokenInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)
                .maxAge(3600);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/**") // 拦截所有 /api/** 路径的请求
                .excludePathPatterns("/api/users/login")// 排除登录接口
                .excludePathPatterns("/api/users/register")
                .excludePathPatterns("/api/users/checkName")
                .excludePathPatterns("/api/product/**")
                .excludePathPatterns("/api/admin/login");

    }
}
