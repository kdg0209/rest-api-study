package com.example.kdg.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private int code = HttpStatus.BAD_REQUEST.value();
    private Object error;

    public ErrorResponse(int code, Object error) {
        this.code = code;
        this.error = error;
    }
}
