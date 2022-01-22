package com.example.kdg.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorType {

    UsernameNotFoundException (400, "계정이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    BadCredentialsException (400, "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    DataIsNullException(400, "필수값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    NotNULL(400, "필수값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZEDException (401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ExpiredJwtException(401, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    ReLogin(403, "모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    ;

    @Getter
    private int code;

    @Getter
    private String description;

    @Getter
    private HttpStatus httpStatus;

    ErrorType(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
