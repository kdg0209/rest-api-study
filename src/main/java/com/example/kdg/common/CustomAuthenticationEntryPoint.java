package com.example.kdg.common;

import com.example.kdg.exception.ErrorType;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthRefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtProvider jwtProvider;
    private final AuthRefreshTokenMapper authRefreshTokenMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        ErrorType errorType;

        /**
         * 토큰이 없는 경우 예외처리
         */
        if(exception == null) {
            errorType = ErrorType.UNAUTHORIZEDException;
            setResponse(response, errorType);
            return;
        }

        /**
         * 토큰이 만료된 경우 예외처리
         */
        if(exception.equals("ExpiredJwtException")) {
            errorType = ErrorType.ExpiredJwtException;
            setResponse(response, errorType);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorType.getCode());
        json.put("message", errorType.getDescription());
        response.getWriter().print(json);
    }
}
