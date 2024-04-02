package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Client")
@Entity
@Getter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column
    private String name;

    @Column
    private String phone_no;

    @Column
    private String birth;

    @Column
    private String gender;

    @Column
    private String email;

    @OneToOne//외래키 설정??
    @Column(name = "password_id")
    private PW passwordId;

}
