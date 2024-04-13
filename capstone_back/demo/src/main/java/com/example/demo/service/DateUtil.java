package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDateTime parseStringToLocalDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 포맷을 적절히 조정해주세요.
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
