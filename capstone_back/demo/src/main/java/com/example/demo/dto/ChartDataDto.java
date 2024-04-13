package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartDataDto { // 클라이언트에게 시간대별 사용량 및 관련 상세정보 전달
    private String date; // 날짜
    private int alarm_freq; // 알림 빈도수
}