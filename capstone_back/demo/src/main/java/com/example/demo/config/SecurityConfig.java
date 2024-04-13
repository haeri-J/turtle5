package com.example.demo.config;


import com.example.demo.entity.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 기능 활성화 (기본 설정을 사용하므로 별도로 명시하지 않음)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/info").authenticated()
                        .requestMatchers("/api/admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginPage("/api/login2")
                        .defaultSuccessUrl("/api")
                        .failureUrl("/api/login2?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/api/access-denied")); // 접근 거부 페이지 설정 (옵션)

        return http.build();
    }
}
