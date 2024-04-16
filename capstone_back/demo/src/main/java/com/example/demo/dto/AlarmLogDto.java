package com.example.demo.dto;

import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AlarmLogDto {
    private Long id;
    private LocalDateTime time;
    private LocalDateTime date;

    public AlarmLog toEntity(Client client) {
        return new AlarmLog(id, time, date, client);
    }
}