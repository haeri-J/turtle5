package com.example.demo.service;

import com.example.demo.dto.ChartDataDTO;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.AlarmLogRepository;
import com.example.demo.repository.WebCamLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChartDataService {

    @Autowired
    WebCamLogRepository webCamLogRepository;

    @Autowired
    AlarmLogRepository alarmLogRepository;

//    public List<ChartDataDTO> getChartData() {
//        // 모든 WebCamLog와 AlarmLog를 조회합니다.
//        List<WebCamLog> webcamLogs = webCamLogRepository.findAll();
//        List<AlarmLog> alarmLogs = alarmLogRepository.findAll();
//
//
//
//        // 요일별로 웹캠 실행 시간을 집계합니다.
//        Map<DayOfWeek, Long> webcamDurationByDay = webcamLogs.stream()
//                .collect(Collectors.groupingBy(
//                        log -> log.getStartTime().getDayOfWeek(),
//                        Collectors.summingLong(log -> ChronoUnit.MINUTES.between(log.getStartTime(), log.getEndTime()))
//                ));
//
//        // 요일별로 알람 발생 횟수를 집계합니다.
//        Map<DayOfWeek, Long> alarmCountByDay = alarmLogs.stream()
//                .collect(Collectors.groupingBy(
//                        log -> log.getAlarmTime().getDayOfWeek(),
//                        Collectors.counting()
//                ));
//
//        // 집계된 데이터를 ChartDataDTO 리스트로 변환합니다.
//        List<ChartDataDTO> chartData = new ArrayList<>();
//        for (DayOfWeek day : DayOfWeek.values()) {
//            long webcamDuration = webcamDurationByDay.getOrDefault(day, 0L);
//            long alarmCount = alarmCountByDay.getOrDefault(day, 0L);
//            chartData.add(new ChartDataDTO(day, webcamDuration, alarmCount));
//        }
//
//        return chartData;
//
//    }
}

