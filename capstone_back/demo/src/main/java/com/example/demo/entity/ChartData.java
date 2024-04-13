package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "ChartData")
@Entity
@Getter
@Setter
public class ChartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "alarm_freq", nullable = false)
    private int alarmFreq;

    @Column(nullable = false)
    private int alarm_freq;

}
