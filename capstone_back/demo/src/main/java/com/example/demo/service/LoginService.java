package com.example.demo.service;

import com.example.demo.dto.LoginRequestDto;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public String login(LoginRequestDto requestDTO) {
        // 여기에 로그인 로직을 구현합니다.
        // 예를 들어, 입력된 이메일과 비밀번호를 확인하여 성공이면 "success"를 반환합니다.
        if ("a@a.com".equals(requestDTO.getEmail()) && "abc123".equals(requestDTO.getPassword())) {
            return "success";
        } else {
            return "failure";
        }
    }
}
