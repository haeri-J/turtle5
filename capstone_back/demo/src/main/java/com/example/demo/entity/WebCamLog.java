package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.DoubleStream;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "webCamLog")
@Entity
@Getter
@Setter
public class WebCamLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계 설정
    @JoinColumn(name = "client_id", nullable = false) // 외래키 컬럼 지정
    private Client clientId;


    //   @AllArgsConstructor이 해주는 일은 아래와 같은 일
//    public WebCamLog(Long camLogId, String startTime, String endTime, String camUseDate, Client clientId) {
//        this.id = camLogId;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.camUseDate= camUseDate;
//        this.clientId = clientId;
//    }
}
