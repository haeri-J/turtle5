package com.example.demo.api;


import com.example.demo.dto.AlarmLogDto;
import com.example.demo.dto.WebCamLogDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.WebCamLog;
import com.example.demo.service.AlarmLogService;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.WebCamLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j//log.info를 쓰기 위한 어노테이션
@RestController//컨트롤러라고 명명하는 어노테이션
@RequestMapping
public class LogApiController {

    @Autowired
    private WebCamLogService webcamLogService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlarmLogService alarmLogService;

    // 알람 로그를 저장하는 POST 요청 처리
    @PostMapping("/webcam/alarmlog")//프론트에 맞게 수정 필수
    public ResponseEntity<?> saveAlarmLog(@RequestBody AlarmLogDto alarmLogDto) {

        Long clientId = authenticationService.getCurrentUserId();

        AlarmLog alarmLog = alarmLogService.saveAlarmLog(clientId, alarmLogDto);

        // 저장 성공 시 "success" 메시지와 응답 반환
        return  ResponseEntity.status(HttpStatus.OK).body(alarmLog);
    }


    @PostMapping("/webcam/log")
    public ResponseEntity<WebCamLog> recodeLog(@RequestBody WebCamLogDto webcamlog){

        Long clientId = authenticationService.getCurrentUserId();

        WebCamLog created = webcamLogService.saveLog(clientId,webcamlog);

        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

