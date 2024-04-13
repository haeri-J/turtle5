package com.example.demo.dto;

import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import java.time.LocalDateTime;

import com.example.demo.entity.WebCamLog;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class AlarmLogDto {

    private Long id;
    private LocalDateTime time;
    private LocalDateTime date;
    private Client clientId;

    public AlarmLog toEntity(Client clientId) {
        return new AlarmLog(id, time, date, clientId);
    }
}