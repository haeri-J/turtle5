package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class FindIDDto {


    @NotBlank(message = "이름는 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNo;

}
