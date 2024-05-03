package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.AccessToken;
import com.example.demo.entity.Client;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

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


//    public void logoutUser(String userId) {
//        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByClient_Email(userId);
//        refreshTokenOptional.ifPresent(refreshToken -> {
//            refreshTokenRepository.delete(refreshToken);
//            // 사용자 로그아웃 처리
//        });
//    }

    //현재 이용자를 가져옴..
    public Long getCurrentUserId() {//우선 무조건 1번 회원 리턴하도록 함. 후에 수정 꼭 필요!
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof CustomerUserDetail) {
//            String currentEmail = ((CustomerUserDetail)principal).getUsername();
//            Client client = clientRepository.findByEmail(currentEmail).orElseThrow();
//            return client.getClientId();
//        } else {
//            throw new IllegalStateException("인증된 사용자 정보를 얻을 수 없습니다.");
//        }
        return 1L;
    }

}