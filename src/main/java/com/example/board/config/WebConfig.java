package com.example.board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// 레거시 프로젝트 환경설정은 .xml 로 진행

@Configuration  // boot 환경설정 java config
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                // 모든 경로 인터셉트!!!
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/member/login", "/member/logout", "/member/join") // 인터셉트에서 제외 할 url
                .excludePathPatterns("/js/**", "/css/**", "/img/**", "/favicon.ico");
    }
}
