package com.example.demo.service;

import com.example.demo.dto.CustomerUserDetail;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    ClientRepository clientRepository;

    //현재 이용자를 가져옴..
    public Long getCurrentUserId() {//우선 무조건 1번 회원 리턴하도록 함. 후에 수정 꼭 필요!
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomerUserDetail) {
            String currentEmail = ((CustomerUserDetail)principal).getUsername();
            Client client = clientRepository.findByEmail(currentEmail).orElseThrow();
            return client.getClientId();
        } else {
            throw new IllegalStateException("인증된 사용자 정보를 얻을 수 없습니다.");
        }

    }
}