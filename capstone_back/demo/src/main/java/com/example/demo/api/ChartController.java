package com.example.demo.api;


import com.example.demo.dto.ChartDataDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ChartController {

    @Autowired
    private ChartDataService chartDataService;

    @GetMapping("/inquiry/{clientId}")
    public List<ChartDataDto> getChartData(@RequestParam Client clientId, Model model) {

        List<ChartDataDto> chartdata = chartDataService.getChartData(clientId);

        model.addAttribute("chartData", chartdata);

       return null;

    }
}
