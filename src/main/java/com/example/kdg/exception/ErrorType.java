package com.example.kdg.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorType {

    UsernameOrPasswordNotFoundException (400, "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ForbiddenException(403, "해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZEDException (401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ExpiredJwtException(444, "기존 토큰이 만료되었습니다. 해당 토큰을 가지고 get-newtoken링크로 이동해주세요.", HttpStatus.UNAUTHORIZED),
    ReLogin(445, "모든 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
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
