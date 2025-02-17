package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Data
@Table(name="alarmLog")
@NoArgsConstructor
@AllArgsConstructor
public class AlarmLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmLog_id;

    @Column(name = "alarm_ringTime", nullable = false)
    private LocalDateTime dateTime; // 알람이 발생한 시간과 날짜


    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계 설정
    @JoinColumn(name = "client_id", nullable = false) // 외래키 컬럼 지정
    private Client clientId;


}
