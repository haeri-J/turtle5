package com.example.demo.controller;

import com.example.demo.dto.ClientForm;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j//log.info를 쓰기 위한 어노테이션
@RestController//컨트롤러라고 명명하는 어노테이션
public class ClientApiController {

    @Autowired
    private ClientService clientService;

    // 클라이언트 정보 입력 폼 대신 클라이언트 정보를 받아서 처리하는 API
    @PostMapping("/api/clients")//1.사용자가 이 주소에 들어가면 dto형태로 회원 정보(clientid등등..)가 들어옴(@RequestBody-> post 바디형태 데이터 전달)
    public ResponseEntity<Client> createClient(@RequestBody ClientForm client) {
        Client created = clientService.saveClient(client); //2.클라이언트 정보를 저장하는 로직을 서비스 계층에서 처리(서비스 내 saveClient 호출)
        // 비밀번호 암호화 로직 구현--->해야함 그리고 지금 사용자가 쓴 비번이 pw_id 에 들어가는 상황
        // 4.디비에 잘 저장이 되어 널 값이 아니면
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
