package com.example.demo.api;

import com.example.demo.dto.ChartDataDto;
import com.example.demo.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ChartController {

    @Autowired
    private ChartDataService chartDataService;

    @GetMapping("/chartInquiry")
    public List<ChartDataDto> getChartData(Model model) {

        //List<ChartDataDTO> chartdata = chartDataService.getchartData();
        //model.addAttribute("chartData", chartdata);

    public ResponseEntity<?> getChartData(@PathVariable("clientId") Long clientId, // URL에서 clientId 값을 추출하여 메소드의 인자로 사용
                                          @RequestParam("date") LocalDate date,
                                          @RequestParam("alarmFreq") int alarmFreq) {

        List<ChartDataDto> chartDataDto = chartDataService.findAllChartData();
        return ResponseEntity.ok().body(chartDataDto);

        // return ResponseEntity.ok().body(new ChartDataDto());
    }

    }
}
