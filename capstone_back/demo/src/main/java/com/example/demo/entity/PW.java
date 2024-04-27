package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long passwordId;

    @Column
    private String passwordHash;


//    //== 패스워드 암호화 ==//
//    public void encodePassword(PasswordEncoder passwordEncoder){
//        this.passwordHash = passwordEncoder.encode(passwordHash);
//    }


}
