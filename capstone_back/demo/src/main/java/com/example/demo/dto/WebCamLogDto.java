package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class WebCamLogDto {

    private Long camLogId;
    private String startTime;
    private String endTime;
    private String cam_useDate;


    public WebCamLog toEntity(Client clientId) {
        return  new WebCamLog(camLogId, startTime, endTime, cam_useDate, clientId);
    }
}
