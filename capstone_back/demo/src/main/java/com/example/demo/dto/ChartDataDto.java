package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChartDataDto {
    private Long clientId;
    private LocalDate date;
    private int alarmFreq;
}
