package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "WebCamLog")
@Entity
@Getter
public class WebCamLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time",nullable = false)
    private String startTime;

    @Column(name = "end_time",nullable = false)
    private String endTime;

    @Column(name = "cam_useDate",nullable = false)
    private String camUseDate;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계 설정
    @JoinColumn(name = "client_id", nullable = false) // 외래키 컬럼 지정
    private Client clientId;

}
