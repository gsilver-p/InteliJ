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
        // Controller에서
        .addPathPatterns("/**")  // 전 단계 다 가로채기~!!!
                .excludePathPatterns("/", "/member/login", "/member/logout", "/member/join", "/js/**", "/css/**", "/img/**");

    }
}
