package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    ClientRepository clientRepository;

    // 현재 인증된 사용자 ID를 가져옴.
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("인증된 사용자가 없습니다.");
            throw new IllegalStateException("인증된 사용자 없음.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            Optional<Client> client = clientRepository.findByEmail(userDetails.getUsername());

            if (client.isPresent()) {
                return client.get().getClientId();
            } else {
                log.error("사용자가 데이터베이스에서 찾을 수 없습니다.");
                throw new IllegalStateException("사용자를 찾을 수 없음.");
            }
        } else {
            log.error("principal 객체가 UserDetails의 인스턴스가 아닙니다.");
            throw new IllegalStateException("잘못된 사용자 타입.");
        }
    }


}