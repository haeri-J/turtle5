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
        // Client 엔티티 조회
        Client target = clientRepository.findById(clientId).orElse(null);
        if (target == null) {
            // 적절한 예외 처리나 로직 추가
            return null;
        }

        // dto에서 WebCamLog 엔티티로 변환, 이때 Client 엔티티를 전달
        WebCamLog camLog = dto.toEntity(target);

        // camLog의 ID가 이미 설정되어 있는지 확인하는 로직, 의도에 따라 수정 가능
        if (camLog.getId() != null) {
            return null;
        }

        // WebCamLog 엔티티 저장
        return webCamLogRepository.save(camLog);
    }
}
