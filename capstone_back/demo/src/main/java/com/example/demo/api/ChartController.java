package com.example.demo.api;


import com.example.demo.dto.ChartDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ChartController {
    @GetMapping("/{clientId}/inquiry")
    public ResponseEntity<?> getChartData(@PathVariable("clientId") Long clientId, // URL에서 clientId 값을 추출하여 메소드의 인자로 사용
                                          @RequestParam("start_time") LocalDateTime startTime,
                                          @RequestParam("end_time") LocalDateTime endTime,
                                          @RequestParam("cam_usedDate") LocalDate camUsedDate) {
        // 차트 데이터 조회 로직 구현
        return ResponseEntity.ok().body(new ChartDataDTO());
    }
}