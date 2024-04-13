package com.example.demo.dto;

import com.example.demo.entity.Client;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter

public class AlarmLogDto {
    private Long id;
    private LocalDateTime time;
    private LocalDateTime date;
    private Client clientId;
}