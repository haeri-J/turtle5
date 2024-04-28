package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Table(name = "client")
@Entity
@Getter
@Builder
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column
    private String birth;

    @Column
    private String gender;

    @Column(nullable = false, unique = true, length = 30)
    private String email;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", referencedColumnName = "password_id")
    private PW passwordId;

    private String role;

    public String getPassword() {
        return passwordId.getPasswordHash();
    }
}
