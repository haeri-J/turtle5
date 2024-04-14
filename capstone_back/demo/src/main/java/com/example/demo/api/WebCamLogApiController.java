package com.example.demo.api;


import com.example.demo.dto.WebCamLogDto;
import com.example.demo.entity.WebCamLog;
import com.example.demo.service.WebCamLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j//log.info를 쓰기 위한 어노테이션
@RestController//컨트롤러라고 명명하는 어노테이션
@RequestMapping("/{clientId}/webcam")//시큐리티 적용시 수정 필수
public class WebCamLogApiController {

    @Autowired
    private WebCamLogService webcamLogService;

    @PostMapping("/log")
    public ResponseEntity<WebCamLog> recodeLog(@PathVariable Long clientId , @RequestBody WebCamLogDto webcamlog){
        WebCamLog created = webcamLogService.saveLog(clientId, webcamlog);

        return (created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

