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

    public List<ChartDataDTO> findChartDataByLabel(String label) {
        List<ChartData> chartDataList = chartDataRepository.findByLabelContaining(label);
        // Entity를 DTO로 변환하는 로직 필요
        return chartDataList.stream().map(data -> new ChartDataDTO(data.getLabel(), data.getValue())).collect(Collectors.toList());
    }
}
