package com.scsa.moin_back.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring 서버 전역적으로 CORS 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("**") // 허용 출처 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP method 설정
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }
}