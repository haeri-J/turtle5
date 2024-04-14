package com.example.demo.service;

import com.example.demo.dto.AlarmLogDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.repository.AlarmLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AlarmLogService {
    private final AlarmLogRepository alarmLogRepository;

    @Autowired
    public AlarmLogService(AlarmLogRepository alarmLogRepository) {
        this.alarmLogRepository = alarmLogRepository;
    }


    // 알람 로그 저장 메소드
    public AlarmLog saveAlarmLog(AlarmLogDto alarmLogDto) {


        AlarmLog alarmLog = new AlarmLog();

        // Id, 날짜, 시간 설정
        alarmLog.setClientId((alarmLogDto.getClientId()));
        alarmLog.setDate(alarmLogDto.getDate());
        alarmLog.setTime(alarmLog.getTime());

        // 알람 로그 저장
        return alarmLogRepository.save(alarmLog);
    }
}
