package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.stream.DoubleStream;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "WebCamLog")
@Entity
@Getter
@Setter
public class WebCamLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time",nullable = false)
    private String startTime;

    @Column(name = "end_time",nullable = false)
    private String endTime;

    @Column(name = "cam_usedDate",nullable = false)
    private String camUseDate;

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
