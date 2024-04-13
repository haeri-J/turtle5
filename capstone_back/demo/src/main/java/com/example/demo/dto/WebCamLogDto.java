package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class WebCamLogDto {

    private Long camLogId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime cam_useDate;


    public WebCamLog toEntity(Client clientId) {
        return new WebCamLog(camLogId, startTime, endTime, cam_useDate, clientId);
    }
}
