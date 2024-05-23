package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("https://docturtle.site", "http://localhost:3000") // 여기서는 example.com을 허용된 출처로 설정합니다. 실제 리액트 앱이 호스팅되는 도메인 주소를 사용하세요.
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 허용된 HTTP 메서드
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true); // 쿠키를 포함한 요청 허용
    }

}
