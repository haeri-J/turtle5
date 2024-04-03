package com.example.demo.service;

import com.example.demo.dto.WebCamLogDto;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.WebCamLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebCamLogService {


    @Autowired
    private WebCamLogRepository webCamLogRepository;
    public WebCamLog saveLog(WebCamLogDto dto) {

        WebCamLog camLog = dto.toEntity();
        if(camLog.getId() != null){
            return null;
        }
        return webCamLogRepository.save(camLog);

    }
}