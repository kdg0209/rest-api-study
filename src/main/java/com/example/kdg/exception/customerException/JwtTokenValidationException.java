package com.example.kdg.exception.customerException;

import javax.security.sasl.AuthenticationException;

/**
 * 유효하지 않은 JWT 토큰 예외 클래스
 */
public class JwtTokenValidationException extends AuthenticationException {

    private static final long serialVersionUID = -5959543783324224864L;

    private String token;

    public JwtTokenValidationException(String msg) {
        super(msg);
    }

    public JwtTokenValidationException(String token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }
}
