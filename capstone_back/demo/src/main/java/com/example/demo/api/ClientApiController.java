package com.example.demo.api;

import com.example.demo.dto.*;
import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j//log.info를 쓰기 위한 어노테이션
@RestController//컨트롤러라고 명명하는 어노테이션
public class ClientApiController {

    @Autowired
    private ClientService clientService;// 서비스 객체 주입
    @Autowired
    private AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);

    // 클라이언트 정보 입력 폼 대신 클라이언트 정보를 받아서 처리하는 API
    @PostMapping("/api/signup")//1.사용자가 이 주소에 들어가면 dto형태로 회원 정보(clientid등등..)가 들어옴(@RequestBody-> post 바디형태 데이터 전달)
    public ResponseEntity<Client> createClient(@RequestBody ClientForm client) {
        Client created = clientService.saveClient(client); //2.클라이언트 정보를 저장하는 로직을 서비스 계층에서 처리(서비스 내 saveClient 호출)
        // 4.디비에 잘 저장이 되어 널 값이 아니면
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 로그인 매핑
    @PostMapping("/api/login")
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequestDto loginRequest, HttpServletResponse response) {
        String memberId = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        JwtToken jwtToken = clientService.login(memberId, password);

        if (jwtToken != null) {
            return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 로그아웃 매핑
    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal Client client, @RequestBody JwtToken jwtToken, HttpServletResponse response) {
        // 클라이언트 서비스에서 로그아웃 처리 (예: 토큰 무효화 등)
        clientService.logout(jwtToken.getAccessToken(), client);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    // Controller 계층
    @PostMapping("/api/signup/id_check")
    public ResponseEntity<?> checkEmail(@RequestBody EmailDto emailDto) {
        if(clientService.checkEmailUnique(emailDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일이므로 다른 이메일 아이디를 사용해 주세요.");
        }
        return ResponseEntity.ok(emailDto); // 중복되지 않은 경우, 200 OK 상태 코드와 함께 EmailDto 반환
    }


    //아이디 찾기
    @PostMapping("/api/findID")
    public ResponseEntity<String> findID(@RequestBody FindIDDto findIDDto){
        String created = clientService.checktoFindId(findIDDto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/findPassword")
    public ResponseEntity<String> findPassword(@RequestBody FindPasswordDto findPasswordDto){
        String created = clientService.checktoFindPassword(findPasswordDto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/setPassword")
    public ResponseEntity<?> setPassword(@RequestBody SetPassword setPassword){
        PW created = clientService.setPassword(setPassword);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping("/api/mypage")
    public ResponseEntity<UserInfoDto> getforMypage(){
        Long clientId = authenticationService.getCurrentUserId();
        UserInfoDto created = clientService.getforMypage(clientId);
        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    @GetMapping("/api/Membercount")
    public  ResponseEntity<MemberCount> CountMember(){
        MemberCount created = clientService.countMember();
        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
