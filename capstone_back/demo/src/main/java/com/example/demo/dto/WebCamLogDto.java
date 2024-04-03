package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WebCamLogDto {

    private Long camLogId;
    private String startTime;
    private String endTime;
    private String cam_useDate;
    private Long clientId;

}
