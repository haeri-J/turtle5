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

public class MemberCount { // 클라이언트에게 시간대별 사용량 및 관련 상세정보 전달

    private int Allnum;
    private int Todaynum;

}
