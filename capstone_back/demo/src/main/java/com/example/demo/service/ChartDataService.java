package com.example.demo.service;

import com.example.demo.dto.ChartDataDto;
import com.example.demo.dto.PosturePercentageDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.AlarmLogRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.WebCamLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartDataService {

    @Autowired
    WebCamLogRepository webCamLogRepository;

    @Autowired
    AlarmLogRepository alarmLogRepository;

    @Autowired
    ClientRepository clientRepository;

    //기능1. 차트 데이터 받아오는 함수
    public List<ChartDataDto> getChartData(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Client가 없습니다. ID: " + clientId));


        // 사용자별 WebCamLog와 AlarmLog를 조회합니다.//비어있으면 널값 반환
        List<WebCamLog> webcamLogs = Optional.ofNullable(webCamLogRepository.findByClientId(client)).orElse(Collections.emptyList());
        List<AlarmLog> alarmLogs = Optional.ofNullable(alarmLogRepository.findByClientId(client)).orElse(Collections.emptyList());
        // 요일별로 웹캠 실행 시간을 집계합니다.
        Map<DayOfWeek, Long> webcamDurationByDay = webcamLogs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getStartTime().getDayOfWeek(),
                        Collectors.summingLong(log -> ChronoUnit.MINUTES.between(log.getStartTime(), log.getEndTime()))
                ));

        // 요일별로 알람 발생 횟수를 집계합니다.
        Map<DayOfWeek, Long> alarmCountByDay = alarmLogs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getTime().getDayOfWeek(), // getTime()이 LocalDateTime을 반환한다고 가정합니다.
                        Collectors.counting()
                ));

        // 집계된 데이터를 ChartDataDTO 리스트로 변환합니다.
        List<ChartDataDto> chartData = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            long webcamDuration = webcamDurationByDay.getOrDefault(day, 0L);
            long alarmCount = alarmCountByDay.getOrDefault(day, 0L);
            chartData.add(new ChartDataDto(day, webcamDuration, alarmCount));
        }

        return chartData;
    }

    //기능 2.
    // 사용자의 clientId를 인자로 받아서 웹캠 총 실행 시간을 계산하고, 자세를 올바르게 유지한 시간을 계산(메소드 분리)하여 퍼센티지를 구함.
    public double calculateCorrectPosturePercentage(Long clientId) {
        LocalDate today = LocalDate.now();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Client가 없습니다. ID: " + clientId));

        // 오늘 날짜의 WebCamLog 조회
        List<WebCamLog> todaysWebCamLogs = webCamLogRepository.findByClientIdAndCamUseDate(client, today.atStartOfDay());

        // 오늘 날짜의 AlarmLog 조회
        List<AlarmLog> todaysAlarmLogs = alarmLogRepository.findByClientIdAndDate(client, today.atStartOfDay());

        // 웹캠의 총 실행 시간 계산
        long totalWebCamDuration = todaysWebCamLogs.stream()
                .mapToLong(log -> Duration.between(log.getStartTime().toLocalTime(), log.getEndTime().toLocalTime()).toMinutes())
                .sum();

        // 자세를 올바르게 유지한 시간 계산 (알람 로그를 기반으로)
        long correctPostureDuration = calculateCorrectPostureDuration(todaysAlarmLogs);

        if (totalWebCamDuration == 0) return 0; // 웹캠 실행 시간이 0인 경우를 처리

        // 상위 퍼센트 계산을 위한 로직 추가 필요
        return (double) correctPostureDuration / totalWebCamDuration * 100;
    }

    //사용자의 자세를 올바르게 유지한 시간을 더함.
    private long calculateCorrectPostureDuration(List<AlarmLog> alarmLogs) {
        // 알람 로그를 알람 시간에 따라 오름차순으로 정렬
        alarmLogs.sort(Comparator.comparing(AlarmLog::getTime));

        long correctPostureDuration = 0;
        AlarmLog previousLog = null;

        for (AlarmLog currentLog : alarmLogs) {
            // 첫 번째 로그가 아니라면
            if (previousLog != null) {
                // 이전 알람과 현재 알람 사이의 시간 간격을 계산
                long durationBetweenAlarms = Duration.between(previousLog.getTime().toLocalTime(), currentLog.getTime().toLocalTime()).toMinutes();
                // 자세를 올바르게 유지한 시간에 더함
                correctPostureDuration += durationBetweenAlarms;
            }
            // 현재 로그를 이전 로그로 설정
            previousLog = currentLog;
        }

        return correctPostureDuration;
    }

    // 모든 사용자의 clientId 목록을 가져오는 메서드
    private List<Long> getAllClientIds() {
        return clientRepository.findAll().stream()
                .map(Client::getClientId)
                .collect(Collectors.toList());
    }

    //현재 사용자와 모든 사용자의 자세유지 비율을 계산하여 현재 사용자의 비율이 상위 몇 퍼센트인지를 계산.
    public PosturePercentageDto calculateOverallPostureRank(Long currentClientId) {
        LocalDate today = LocalDate.now();
        // 모든 사용자의 clientId 목록을 가져옴 (가정)
        List<Long> allClientIds = getAllClientIds();

        // 현재 사용자의 자세 유지 비율 계산
        double currentUserPosturePercentage = calculateCorrectPosturePercentage(currentClientId);

        // 모든 사용자의 자세 유지 비율 계산
        List<Double> allUsersPosturePercentages = new ArrayList<>();
        for (Long clientId : allClientIds) {
            double posturePercentage = calculateCorrectPosturePercentage(clientId);
            allUsersPosturePercentages.add(posturePercentage);
        }

        // 현재 사용자의 비율이 상위 몇 퍼센트인지 계산
        long higherCount = allUsersPosturePercentages.stream()
                .filter(percentage -> percentage > currentUserPosturePercentage)
                .count();

        // 상위 퍼센트 계산// 반환 값이 90이면, 현재 사용자의 자세 유지 비율이 모든 사용자 중 상위 10%에 해당함을 의미
        double rankPercentage = (1 - ((double) higherCount / allUsersPosturePercentages.size())) * 100;
        return new PosturePercentageDto(currentClientId, currentUserPosturePercentage, rankPercentage);
    }


}

