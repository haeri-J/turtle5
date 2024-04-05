package com.example.demo.repository;

import com.example.demo.entity.ChartData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChartDataRepository extends JpaRepository<ChartData, Long> {
    List<ChartData> findByLabelContaining(String label);
}
