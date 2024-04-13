package com.example.demo.dto;

import com.example.demo.entity.Client;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class AlarmLogDto {

    private Long alarmLog_id;
    private String alarmDate;
    private String alarm_ringTime;

    public AlarmLogDto toEntity(Client clientId) {
        return new AlarmLogDto(alarmLog_id, alarmDate, alarm_ringTime, clientId);
    }
}