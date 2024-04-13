package com.example.demo.dto;

import com.example.demo.entity.Client;
import com.example.demo.entity.PW;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ClientForm {//dto


    private Long clientId;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNo;
    @NotBlank(message = "생일은 필수 입력 값입니다.")
    private String birth;
    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String gender;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
    private String password; // 외래키를 나타내는 필드는 ID만 전달하도록 합니다.

    //dto를 entity로 변환하는 함수
    public Client toEntity(PW passwordEntity) {
        return new Client(clientId, name,phoneNo, birth, gender, email, passwordEntity);
    }
}
