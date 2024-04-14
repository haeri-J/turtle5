package com.example.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    //현재 이용자를 가져옴..
    public Long getCurrentUserId() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof CustomUserDetails) {
//            return ((CustomUserDetails)principal).getUserId();
//        } else {
//            throw new IllegalStateException("인증된 사용자 정보를 얻을 수 없습니다.");
//        }
        return null;
    }
}