package com.example.demo.api;


import com.example.demo.dto.AlarmLogDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.service.AlarmLogService;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmLogController {
    @Autowired
    private AlarmLogService alarmLogService;

    @Autowired
    private AuthenticationService authenticationService;

    // 알람 로그를 저장하는 POST 요청 처리
    @PostMapping("/webcam/alarmlog")//프론트에 맞게 수정 필수
    public ResponseEntity<?> saveAlarmLog(@RequestBody AlarmLogDto alarmLogDto) {

        Long clientId = authenticationService.getCurrentUserId();

        AlarmLog alarmLog = alarmLogService.saveAlarmLog(clientId, alarmLogDto);

        // 저장 성공 시 "success" 메시지와 응답 반환
        return  ResponseEntity.status(HttpStatus.OK).body(alarmLog);
    }
}
