package com.example.kdg.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int code = HttpStatus.BAD_REQUEST.value();
    private Object error;
}
