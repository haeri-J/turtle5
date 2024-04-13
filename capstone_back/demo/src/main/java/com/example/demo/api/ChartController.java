package com.example.demo.api;


import com.example.demo.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChartController {

    @Autowired
    private ChartDataService chartDataService;

//    @GetMapping("/inquiry")
//    public List<ChartDataDTO> getChartData(Model model) {
//
//        //List<ChartDataDTO> chartdata = chartDataService.getchartData();
//
//        //model.addAttribute("chartData", chartdata);
//
//       return
//
//    }
}