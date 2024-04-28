package com.example.demo.jwt;

import com.example.demo.dto.CustomerUserDetail;
import com.example.demo.entity.Client;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private  final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("Token null");
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Authorization now");
        String token = authorization.substring(7); // "Bearer " 다음 문자열을 가져오기 위해 split 대신 substring 사용

        if (jwtUtil.isExpired(token)) {
            System.out.println("Token expired");
            filterChain.doFilter(request, response);
            return;
        }


        Client client = new Client();
        client.setEmail(jwtUtil.getUsername(token));
        client.setRole(jwtUtil.getRole(token));

        CustomerUserDetail customerUserDetails = new CustomerUserDetail(client);



        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customerUserDetails, null, customerUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
