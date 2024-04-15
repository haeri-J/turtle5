package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.entity.WebCamLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WebCamLogRepository extends JpaRepository<WebCamLog, Long> {
    List<WebCamLog> findByClientId(Client clientId);

    List<WebCamLog> findByClientIdAndCamUseDate(Client clientId, LocalDateTime camUseDate);
}
