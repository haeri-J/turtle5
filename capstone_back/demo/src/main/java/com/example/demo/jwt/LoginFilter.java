package com.example.demo.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override // 1번 인증
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        //사용자가 입력한 로그인 정보(ID/PW)를 추출
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
        // 토큰 생성 후, 아이디 패스워드 담아서 매니저에게 전달

//        AutehnticationManager 의 역할
//
//        데이터베이스와 연동하여 실제 사용자 정보 조회 및 인증 확인 수행
//        인증 성공/실패 여부 판단 후 결과 반환
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        System.out.println("success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("fail");
    }
}

