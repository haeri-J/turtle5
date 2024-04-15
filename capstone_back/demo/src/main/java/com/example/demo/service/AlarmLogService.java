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
    AlarmLogRepository alarmLogRepository;

    @Autowired
    ClientRepository clientRepository;


    // 알람 로그 저장 메소드
    public AlarmLog saveAlarmLog(Long clientId, AlarmLogDto alarmLogDto) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Client가 없습니다. ID: " + clientId));

        AlarmLog alarmLog = alarmLogDto.toEntity(client);

        // 알람 로그 저장
        return alarmLog.getId() != null ? null : (AlarmLog)this.alarmLogRepository.save(alarmLog);
    }
}
