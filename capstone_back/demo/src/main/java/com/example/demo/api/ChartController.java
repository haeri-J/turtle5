package com.example.demo.api;


import com.example.demo.dto.ChartDataDto;
import com.example.demo.dto.PosturePercentageDto;
import com.example.demo.entity.Client;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ChartController {

    @Autowired
    private ChartDataService chartDataService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/inquiry")
    public ResponseEntity<List<ChartDataDto>> getChartData() {

        Long clientId = authenticationService.getCurrentUserId();

        List<ChartDataDto> chartData = chartDataService.getChartData(clientId);

        return ResponseEntity.status(HttpStatus.OK).body(chartData); // ResponseEntity를 사용하여 데이터와 HTTP 상태 코드를 함께 반환
    }


    @GetMapping("/percentage")
    public ResponseEntity<PosturePercentageDto> getPercentage() {

        Long clientId = authenticationService.getCurrentUserId();

        PosturePercentageDto percentage = chartDataService.calculateOverallPostureRank(clientId);

        return ResponseEntity.status(HttpStatus.OK).body(percentage); // ResponseEntity를 사용하여 데이터와 HTTP 상태 코드를 함께 반환
    }


}
