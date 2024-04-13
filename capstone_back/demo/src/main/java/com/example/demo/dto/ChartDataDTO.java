package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChartDataDTO {
    private DayOfWeek dayOfWeek; // 요일
    private long webcamDuration; // 웹캠 실행 시간(분 단위)
    private long alarmCount; // 알람 발생 횟수

}
