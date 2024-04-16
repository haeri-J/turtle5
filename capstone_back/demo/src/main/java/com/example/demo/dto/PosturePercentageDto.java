package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosturePercentageDto {

    private Long clientId;
    private double posturePercentage;//현재 사용자의 자세비율 퍼센티지
    private double rankPercentage;//상위 랭크 퍼센티지


}
