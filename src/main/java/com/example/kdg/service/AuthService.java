package com.example.kdg.service;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.exception.ErrorType;
import com.example.kdg.exception.customerException.AuthException;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;

    public ApiResponse login(AuthDto authDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ResponseMap result = new ResponseMap();
        AuthDao authDao = authMapper.findByAccountUserId(authDto);

        if(authDao == null){
            throw new AuthException(ErrorType.UsernameNotFoundException);
        }

        if(!encoder.matches(authDto.getUserPwd(), authDao.getUserPwd())){
            throw new AuthException(ErrorType.BadCredentialsException);
        }

        String accessToken = jwtProvider.createJwtToken(authDto);
        String refreshToken = jwtProvider.createRefreshToken(authDto);

        result.setResponseData("accessToken", accessToken);
        result.setResponseData("refreshToken", refreshToken);

        return result;
    }
}
