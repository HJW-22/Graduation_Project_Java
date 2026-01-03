package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 允许所有路径
                        .allowedOrigins("http://localhost:5173") // 将 '*' 替换为具体的前端地址
                        .allowCredentials(true)  // 允许发送 Cookie 信息
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的 HTTP 方法
                        .allowedHeaders("*")  // 允许的请求头
                        .exposedHeaders("*");  // 允许暴露的响应头
            }
        };
    }
}