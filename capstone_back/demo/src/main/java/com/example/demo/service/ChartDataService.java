package com.example.demo.service;

import com.example.demo.dto.ChartDataDto;
import com.example.demo.dto.PosturePercentageDto;
import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.AlarmLogRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.WebCamLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
                //webcamLogs 리스트를 스트림으로 변환한 후, Collectors.groupingBy()를 사용하여 요일별로 로그를 그룹화합니다.
                .collect(Collectors.groupingBy(
                        //log.getStartTime().getDayOfWeek()를 통해 각 로그의 시작 시간으로부터 요일을 추출(getDayOfWeek())합니다
                        log -> log.getStartTime().getDayOfWeek(),
                        //Collectors.summingLong()을 사용하여 같은 요일에 속하는 로그들의 실행 시간(시작 시간과 종료 시간의 차이)을 분 단위(ChronoUnit.MINUTES.between)로 합산합니다.
                        Collectors.summingLong(log -> ChronoUnit.MINUTES.between(log.getStartTime(), log.getEndTime()))
                ));

        // 요일별로 알람 발생 횟수를 집계합니다.
        Map<DayOfWeek, Long> alarmCountByDay = alarmLogs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getDateTime().getDayOfWeek(), // getTime()이 LocalDateTime을 반환한다고 가정합니다.
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

    // 사용자의 자세를 올바르게 유지하지 않은 시간을 계산
    private long calculateIncorrectPostureDuration(List<AlarmLog> todaysAlarmLogs) {

        long incorrectPostureDuration = todaysAlarmLogs.size() * 5L; // 알람 하나당 5분

        log.info("Incorrect Posture Duration Calculated: {} minutes", incorrectPostureDuration);

        return incorrectPostureDuration;
    }


    // 사용자의 clientId를 인자로 받아서 웹캠 총 실행 시간을 계산하고, 자세를 올바르게 유지한 시간을 계산(메소드 분리)하여 퍼센티지를 구함.
    public double calculateCorrectPosturePercentage(Long clientId) {

        LocalDate todayDate = LocalDate.now();
        LocalDateTime startOfDay = todayDate.atStartOfDay();
        LocalDateTime endOfDay = todayDate.atTime(LocalTime.MAX);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Client가 없습니다. ID: " + clientId));

        log.info("Calculating Correct Posture Percentage for Client ID: {}", clientId); // 로그 추가

        // 오늘 날짜의 WebCamLog 조회
        List<WebCamLog> todaysWebCamLogs = webCamLogRepository.findByClientIdAndStartTimeBetween(client, startOfDay, endOfDay);

        // 오늘 날짜의 AlarmLog 조회
        List<AlarmLog> todaysAlarmLogs = alarmLogRepository.findByClientIdAndDateTimeBetween(client, startOfDay, endOfDay);

        log.info("todaysWebCamLogs: {}",todaysWebCamLogs);
        log.info("todaysAlarmLogs: {}",todaysAlarmLogs);

        // 웹캠의 총 실행 시간 계산
        long totalWebCamDuration = todaysWebCamLogs.stream()
                .mapToLong(log -> Duration.between(log.getStartTime().toLocalTime(), log.getEndTime().toLocalTime()).toMinutes())
                .sum();

        // 자세를 올바르게 유지하지 않은 시간 계산 (알람 로그를 기반으로)
        long IncorrectPostureDuration = calculateIncorrectPostureDuration(todaysAlarmLogs);

        if (totalWebCamDuration == 0) return 0; // 웹캠 실행 시간이 0인 경우를 처리

        // IncorrectPostureDuration가 totalWebCamDuration 보다 크지 않다는 조건 추가
        if (IncorrectPostureDuration >= totalWebCamDuration) {
            // 이 경우에는 비율 계산이 의미가 없거나 잘못될 수 있으므로, 0 또는 다른 적절한 값으로 처리
            // 예를 들어, 100% 부정확한 자세로 해석할 수도 있으므로 0을 반환할 수 있음
            return 0;
        }

        double percentage = (double) ((totalWebCamDuration-IncorrectPostureDuration) / totalWebCamDuration * 100);

        log.info("Total WebCam Duration: {}, Correct Posture Duration: {}, Percentage: {}", totalWebCamDuration, IncorrectPostureDuration, percentage); // 로그 추가
        return percentage;
    }

    // 모든 사용자의 clientId 목록을 가져오는 메서드
    private List<Long> getAllClientIds() {
        return clientRepository.findAll().stream()
                .map(Client::getClientId)
                .collect(Collectors.toList());
    }

    //현재 사용자와 모든 사용자의 자세유지 비율을 계산하여 현재 사용자의 비율이 상위 몇 퍼센트인지를 계산.
    public PosturePercentageDto calculateOverallPostureRank(Long currentClientId) {
        // 모든 사용자의 clientId 목록을 가져옴 (가정)
        List<Long> allClientIds = getAllClientIds();

        log.info("Calculating Overall Posture Rank for Client ID: {}", currentClientId); // 로그 추가

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

        log.info("Current User Posture Percentage: {}, Rank Percentage: {}", currentUserPosturePercentage, rankPercentage); // 로그 추가
        return new PosturePercentageDto(currentClientId, currentUserPosturePercentage, rankPercentage);
    }


}

