package com.example.demo.api;


import com.example.demo.dto.AlarmLogDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.service.AlarmLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlarmLogController {
    private final AlarmLogService alarmLogService;

    @Autowired
    public AlarmLogController(AlarmLogService alarmLogService) {
        this.alarmLogService = alarmLogService;
    }

    // 알람 로그를 저장하는 POST 요청 처리
    @PostMapping("/alarmlog")
    public ResponseEntity<?> saveAlarmLog(@RequestBody AlarmLogDto alarmLogDto) {
        AlarmLog alarmLog = alarmLogService.saveAlarmLog(alarmLogDto);

        // 저장 성공 시 "success" 메시지와 응답 반환
        return ResponseEntity.ok().body("success");
    }
}
