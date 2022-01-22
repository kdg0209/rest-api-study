package com.example.kdg.service;

import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.exception.ErrorType;
import com.example.kdg.exception.customerException.AuthenticationException;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthRefreshTokenMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthRefreshTokenMapper authRefreshTokenMapper;

    public ApiResponse login(AuthDto authDto){
        // https://oingdaddy.tistory.com/206
        ResponseMap result = new ResponseMap();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUserId(), authDto.getUserPwd())
            );

            String accessToken = jwtProvider.createJwtToken(authDto.getUserId());
            String refreshToken = jwtProvider.createRefreshToken(authDto.getUserId());
            authRefreshTokenMapper.insertRefreshToken(authDto.getUserId(), accessToken, refreshToken);

            result.setResponseData("accessToken", accessToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
