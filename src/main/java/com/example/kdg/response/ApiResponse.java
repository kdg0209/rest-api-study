package com.example.kdg.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ApiResponse {

    private int code = HttpStatus.OK.value();
    private Object result;

    public ApiResponse() {}

    public ApiResponse(Object result) {
        this.result = result;
    }

    public ApiResponse(int code, Object result) {
        this.code = code;
        this.result = result;
    }

    protected void setResult (Object result){
        this.result = result;
    }
}
