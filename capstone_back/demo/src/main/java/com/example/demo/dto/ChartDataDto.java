package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
public class ChartDataDto { // 클라이언트에게 시간대별 사용량 및 관련 상세정보 전달
    
    private DayOfWeek dayOfWeek; // 요일
    private long webcamDuration; // 웹캠 실행 시간(분 단위)
    private long alarmCount; // 알람 발생 횟수
}