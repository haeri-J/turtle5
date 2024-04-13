package com.example.demo.service;

import com.example.demo.dto.AlarmLogDto;
import com.example.demo.dto.WebCamLogDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.AlarmLogRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AlarmLogService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private final AlarmLogRepository alarmLogRepository;

    @Autowired
    public AlarmLogService(AlarmLogRepository alarmLogRepository) {
        this.alarmLogRepository = alarmLogRepository;
    }

    // 알람 로그 저장 메소드
    public AlarmLog saveAlarmLog(Long clientId, AlarmLogDto alarmLogDto) {
        Client target = AlarmLogRepository.findByUserId(clientId).orElse(null);

        AlarmLog alarmLog = alarmLogDto.toEntity(target);

        if (alarmLog.getAlarmLog_id() != null) {
            return null;
        }

        // Id, 날짜, 시간 설정
        alarmLog.setClientId((alarmLogDto.getClientId()));
        alarmLog.setDate(alarmLogDto.getDate());
        alarmLog.setTime(alarmLog.getTime());

        // 알람 로그를 엔티티에 저장
        return AlarmLogRepository.save(alarmLog);
    }
}
