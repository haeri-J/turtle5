package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Data
@Table(name="AlarmLog")
@NoArgsConstructor
@AllArgsConstructor
public class AlarmLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alarm_ringTime", nullable = false)
    private LocalDateTime time; // 알람이 발생한 시간

    @Column(name = "alarmDate", nullable = false)
    private LocalDateTime date; // 알람이 발생한 날짜


    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계 설정
    @JoinColumn(name = "client_id", nullable = false) // 외래키 컬럼 지정
    private Client clientId;


}
