package com.example.demo.dto;

import com.example.demo.entity.Client;
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
    private Client clientId;
}