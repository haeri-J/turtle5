package com.example.demo.config;

//import com.example.demo.service.ClientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private ClientService clientService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable() // CSRF 보호 기능 비활성화
//                .authorizeRequests()
//                .antMatchers("/api/signup", "/api/login").permitAll() // 회원가입과 로그인 API는 인증 없이 접근 허용
//                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/api/login") // 로그인 처리 URL
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트 URL
//                .permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(clientService) // 사용자 세부 서비스 설정
//                .passwordEncoder(new BCryptPasswordEncoder()); // 비밀번호 인코더 설정
//    }
//}
//

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 기능 비활성화
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

        // 필요한 경우 추가적인 필터 설정
        // http.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

