package com.example.kdg.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {
    LocalDateTime localDateTime = LocalDateTime.now();

    public String getCurrentDateTime(){
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        return format;
    }
}
