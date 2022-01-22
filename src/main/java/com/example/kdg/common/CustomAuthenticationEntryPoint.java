package com.example.kdg.common;

import com.example.kdg.exception.ErrorType;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthRefreshTokenMapper;
import com.example.kdg.response.ResponseMap;
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
            String token = jwtProvider.resolveToken((HttpServletRequest) request);
            String refreshToken = authRefreshTokenMapper.findByAccessToken(token);

            // AccessToken은 만료되었지만 RefreshToken은 만료되지 않은 경우
            if(jwtProvider.validateJwtToken(request, refreshToken)){
                String userId = jwtProvider.getUserInfo(refreshToken);
                String newAccessToken = jwtProvider.createJwtToken(userId);
                String newRefreshToken = jwtProvider.createRefreshToken(userId);

                authRefreshTokenMapper.insertRefreshToken(userId, newAccessToken, newRefreshToken);
                authRefreshTokenMapper.deleteByAccessToken(token);

                newAccessToken(response, newAccessToken);
                return;
            }else{
                // RefreshToken 또한 만료된 경우는 로그인을 다시 진행해야 한다.
                errorType = ErrorType.ReLogin;
                setResponse(response, errorType);
                return;
            }
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

    private void newAccessToken(HttpServletResponse response, String token) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", 777);
        json.put("message", "기존 토큰이 만료되었습니다. 새로운 토큰을 발급하였습니다.");
        json.put("accessToken", token);
        response.getWriter().print(json);
    }
}
