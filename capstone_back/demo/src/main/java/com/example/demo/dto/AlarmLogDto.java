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
    private LocalDateTime dateTime;

    public AlarmLog toEntity(Client client) {
        return new AlarmLog(id, dateTime, client);
    }
}