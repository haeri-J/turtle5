package com.example.demo.service;

import com.example.demo.dto.ChartDataDTO;
import com.example.demo.entity.ChartData;
import com.example.demo.repository.ChartDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartDataService {
    private final ChartDataRepository chartDataRepository;

    @Autowired
    public ChartDataService(ChartDataRepository chartDataRepository) {
        this.chartDataRepository = chartDataRepository;
    }
    
    public List<ChartDataDTO> findAllChartData() {
        List<ChartData> chartDataList = chartDataRepository.findAll();
        return chartDataList.stream().map(data -> {
            ChartDataDTO dto = new ChartDataDTO();
            dto.setPercentile(data.getPercentile());
            return dto;
        }).collect(Collectors.toList());
    }
}
