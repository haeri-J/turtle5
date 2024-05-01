package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface WebCamLogRepository extends JpaRepository<WebCamLog, Long> {
    List<WebCamLog> findByClientId(Client clientId);
    List<WebCamLog> findByClientIdAndStartTimeBetween(Client clientId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
