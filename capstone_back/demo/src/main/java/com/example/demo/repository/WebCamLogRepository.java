package com.example.demo.repository;

import com.example.demo.entity.WebCamLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebCamLogRepository extends JpaRepository<WebCamLog, Long> {
    List<WebCamLog> findByClientId(Long clientId);
}
