package com.example.demo.service;

import com.example.demo.dto.ChartDataDto;
//import com.example.demo.entity.ChartData;
import com.example.demo.entity.ChartData;
import com.example.demo.repository.ChartDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartDataService {

    private ChartDataRepository chartDataRepository;

    @Autowired
    public ChartDataService(ChartDataRepository chartDataRepository) {
        this.chartDataRepository = chartDataRepository;
    }

    public List<ChartDataDto> findAllChartData() {
        List<ChartData> chartDataList = chartDataRepository.findAll();
        return chartDataList.stream().map(data -> {
            ChartDataDto dto = new ChartDataDto();
            dto.setDate(String.valueOf(data.getDate()));
            dto.setAlarm_freq(data.getAlarmFreq());
            return dto;
        }).collect(Collectors.toList());
    }
}
