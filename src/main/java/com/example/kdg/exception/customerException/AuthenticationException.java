package com.example.kdg.exception.customerException;

import com.example.kdg.exception.ErrorCode;

public class AuthenticationException extends RuntimeException{

    private final ErrorCode errorType;

    public AuthenticationException(ErrorCode errorType) {
        this.errorType = errorType;
    }
}
