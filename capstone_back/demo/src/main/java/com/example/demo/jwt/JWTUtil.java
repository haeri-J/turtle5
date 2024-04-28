package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    //토큰 Payload에 저장될 정보
    //- username
    //- role
    //- 생성일
    //- 만료일

    private final SecretKey secretKey; // JWT 토큰 객체 키를 저장할 시크릿 키
    private long expiredMs = 3600000; // 1시간 (1시간 = 60분 = 3600초 = 3600000밀리초)
    //JWTUtil 생성자
    public JWTUtil(@Value("${spring.jwtsecretkey}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    //username 확인 메소드
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    //role 확인 메소드
    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
    }
    // 만료일 확인 메소드
    public boolean isExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    // secretKey와 expiredMs는 예시로 설정한 값입니다.
    // 실제로는 application.properties 등에서 안전하게 관리하는 것이 좋습니다.

    public String createJwt(String username, Collection<?> roles) {
        String roleString = roles.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .claim("username", username)
                .claim("role", roleString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}