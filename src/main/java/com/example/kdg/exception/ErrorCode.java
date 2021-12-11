package com.example.kdg.exception;

import lombok.Getter;

public enum ErrorCode {

    NotNULL(400, "필수값이 누락되었습니다.");

    @Getter
    private int code;

    @Getter
    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
