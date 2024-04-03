package com.example.demo.repository;

import com.example.demo.entity.WebCamLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebCamLogRepository extends JpaRepository<WebCamLog, Long> {
}
