package com.example.demo.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@SpringBootApplication
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정
                .csrf(csrf -> {
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // CSRF 토큰을 쿠키로 제공
                    csrf.ignoringRequestMatchers("/api/**"); // "/api/**" 경로에 대해 CSRF 보호를 비활성화
                })
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/api/signup", "/api/login").permitAll() // 홈, 회원가입, 로그인 페이지는 인증 없이 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 URL 설정
                        .loginProcessingUrl("/api/login") // 로그인 처리 URL
                        .usernameParameter("email") // 사용자 이름 파라미터 이름
                        .passwordParameter("password") // 비밀번호 파라미터 이름
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트될 URL
                        .permitAll() // 모든 사용자가 로그인 페이지 접근 가능
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // 로그아웃 성공 시 리다이렉트될 URL
                        .permitAll()
                );

        return http.build();
    }
    // 필요한 경우 추가적인 필터 설정
    // http.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);
}

