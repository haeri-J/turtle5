package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AlarmLogDto {
    private Long id;
    private LocalTime time;
    private LocalDate date;
    private String clientId;
}