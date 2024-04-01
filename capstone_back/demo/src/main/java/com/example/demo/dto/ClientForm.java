package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class ClientForm {//dto

    private Long clientId;
    private String name;
    private String phoneNo;
    private String birth;
    private String gender;
    private String password; // 외래키를 나타내는 필드는 ID만 전달하도록 합니다.

    //dto를 entity로 변환하는 함수
    public Client toEntity(PW passwordEntity) {
        return new Client(clientId, name,phoneNo, birth, gender, passwordEntity);
    }
}
