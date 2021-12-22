package com.example.kdg.exception.customerException;

import com.example.kdg.exception.ErrorType;

public class AuthenticationException extends RuntimeException{

    private ErrorType errorType;

    public AuthenticationException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
