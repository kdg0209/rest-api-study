package com.example.kdg.exception.customerException;

import com.example.kdg.exception.ErrorType;

public class AuthException extends RuntimeException{

    private ErrorType errorType;

    public AuthException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
