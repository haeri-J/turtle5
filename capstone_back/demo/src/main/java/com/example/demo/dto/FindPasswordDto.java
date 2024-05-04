package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class FindPasswordDto {

    @NotBlank(message = "이름는 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNo;
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String email;
    
}
