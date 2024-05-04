package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.AccessToken;
import com.example.demo.entity.Client;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;



@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    ClientRepository clientRepository;

    public AccessToken refreshToken(String token) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);

        refreshTokenOptional.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // 새로운 액세스 토큰 발급 로직 -> 함수(createNewAccessToken) 호출로 구현
        AccessToken accessToken = createNewAccessToken(refreshTokenOptional.get().getClient());

        return accessToken;
    }

    private static final long EXPIRATION_TIME = 900_000; // 15 minutes in milliseconds
    private static final String SECRET = "4f3dc1b5f5774bc3bec9b16a3331df42bdc27af07186b77b9b6533b5d2f44d13"; // 비밀키

    public AccessToken createNewAccessToken(Client client) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = JWT.create()
                .withSubject(client.getClientId().toString())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withClaim("username", client.getUsername())
                .sign(Algorithm.HMAC512(SECRET));

        return new AccessToken(token, expiryDate);
    }

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