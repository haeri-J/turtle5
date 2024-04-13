package com.example.demo.repository;

import com.example.demo.entity.AlarmLog;
import com.example.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Long> {

    List<AlarmLog> findByClientId(Client clientId);
}