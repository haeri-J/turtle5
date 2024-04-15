package com.example.demo.service;

import com.example.demo.dto.WebCamLogDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.WebCamLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebCamLogService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebCamLogRepository webCamLogRepository;

    // clientId를 Long 타입으로 받는 것으로 변경
    public WebCamLog saveLog(Long clientId, WebCamLogDto dto) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Client가 없습니다. ID: " + clientId));


        // dto에서 WebCamLog 엔티티로 변환, 이때 Client 엔티티를 전달
        WebCamLog camLog = dto.toEntity(client);

        return camLog.getId() != null ? null : (WebCamLog)this.webCamLogRepository.save(camLog);
    }
}
