package com.example.demo.api;

import com.example.demo.dto.*;
import com.example.demo.entity.Client;
import com.example.demo.jwt.JWTUtil;
import com.example.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j//log.info를 쓰기 위한 어노테이션
@RestController//컨트롤러라고 명명하는 어노테이션
public class ClientApiController {

    @Autowired
    private ClientService clientService;// 서비스 객체 주입
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);

    // 클라이언트 정보 입력 폼 대신 클라이언트 정보를 받아서 처리하는 API
    @PostMapping("/signup")//1.사용자가 이 주소에 들어가면 dto형태로 회원 정보(clientid등등..)가 들어옴(@RequestBody-> post 바디형태 데이터 전달)
    public ResponseEntity<Client> createClient(@RequestBody ClientForm client) {
        Client created = clientService.saveClient(client); //2.클라이언트 정보를 저장하는 로직을 서비스 계층에서 처리(서비스 내 saveClient 호출)
        // 4.디비에 잘 저장이 되어 널 값이 아니면
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //로그인 매핑
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        logger.info("로그인 시도: {}", loginRequest.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 성공 후, JWT 생성
            CustomerUserDetail userDetails = (CustomerUserDetail) authentication.getPrincipal();

            String jwt = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getAuthorities()); // 1시간 만료

            logger.info("로그인 성공: {}", loginRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (AuthenticationException e) {
            logger.error("로그인 실패: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    // Controller 계층
    @PostMapping("/signup/id_check")
    public ResponseEntity<?> checkEmail(@RequestBody EmailDto emailDto) {
        if(clientService.checkEmailUnique(emailDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일이므로 다른 이메일 아이디를 사용해 주세요.");
        }
        return ResponseEntity.ok(emailDto); // 중복되지 않은 경우, 200 OK 상태 코드와 함께 EmailDto 반환
    }
}
