package com.example.demo.config;

import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 메소드 이름, 파라미터 타입, 예외 처리 올바르게 수정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 인증 비활성화
                .csrf(csrf -> csrf.disable()) // CSRF 보호 기능 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 관리를 스프링 시큐리티가 아니라 STATELESS로 설정, 즉 세션을 사용하지 않음

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/", "/signup/**", "/findID", "/findPassword", "/setPassword").permitAll() // 올바른 메소드 이름으로 수정
                        .requestMatchers("/logout","/inquery","/percentage","/webcam/**").hasRole("USER") // 올바른 권한명으로 수정
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 URL 지정
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK); // 로그아웃 성공 시 HTTP 상태 코드 200 반환
                            // 필요에 따라 JSON 응답을 설정할 수 있습니다.
                        })
                        .permitAll() // 인증 없이 로그아웃 URL에 접근 허용
                );


        return http.build();
    }
}
