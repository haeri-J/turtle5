package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WebCamLogDto {

    private Long camLogId;
    private String startTime;
    private String endTime;
    private String cam_useDate;
    private Client clientId;

    public WebCamLog toEntity() {
        return  new WebCamLog(camLogId, startTime, endTime, cam_useDate, clientId);
    }
}
