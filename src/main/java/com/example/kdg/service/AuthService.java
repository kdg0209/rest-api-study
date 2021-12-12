package com.example.kdg.service;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;

    public ApiResponse login(AuthDto authDto){
        ResponseMap result = new ResponseMap();
        AuthDao authDao = authMapper.findByAccountUserId(authDto);

        if(authDao == null){
            System.out.println("해당 유저가 존재하지 않습니다.");
        }

        if(!BCrypt.checkpw(authDto.getUserPwd(), authDao.getUserPwd())){
            System.out.println("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createJwtToken(authDto);
        String refreshToken = jwtProvider.createRefreshToken(authDto);

        result.setResponseData("accessToken", accessToken);
        result.setResponseData("refreshToken", refreshToken);

        return result;
    }
}
