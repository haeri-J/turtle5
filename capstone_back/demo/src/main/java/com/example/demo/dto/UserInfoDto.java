package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private  String name;
    private  String email;
    private String phoneNo;
    private  int alarmCount;
    private int webcamDuration;

}
