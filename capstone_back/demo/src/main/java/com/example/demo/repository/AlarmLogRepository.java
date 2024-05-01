package com.example.demo.repository;

import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Long> {

    List<AlarmLog> findByClientIdAndDateTimeBetween(Client client, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<AlarmLog> findByClientId(Client clientId);


}