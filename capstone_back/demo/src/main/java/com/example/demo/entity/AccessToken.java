package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

public class AccessToken {

    @Id
    private String token;
    private String userIdentifier;
    private Date expirationDateTime;

    public AccessToken(String token, Date expiryDate) {
        this.token = token;
        expirationDateTime = expiryDate;
    }
}
