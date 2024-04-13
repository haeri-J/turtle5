package com.example.demo.repository;

import com.example.demo.dto.AlarmLogDto;
import com.example.demo.entity.AlarmLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Long> {

    static AlarmLog save(AlarmLogDto alarmLog) {
    }
}