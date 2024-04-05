package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class ChartData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private Double value;

}
