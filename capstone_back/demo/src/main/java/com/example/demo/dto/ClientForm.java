package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ClientForm {//dto

    private int clientId;
    private String name;
    private String phoneNo;
    private String birth;
    private String gender;
    private PW passwordId; // 외래키를 나타내는 필드는 ID만 전달하도록 합니다.

    //dto를 entity로 변환하는 함수
    public Client toEntity() {
        return new Client(clientId, name,phoneNo, birth, gender, passwordId);
    }
}
