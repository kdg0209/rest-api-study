package com.example.kdg.service;

import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.dto.auth.GetNewAccessToken;
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

import javax.servlet.http.HttpServletRequest;


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

    public ApiResponse newAccessToken(GetNewAccessToken getNewAccessToken, HttpServletRequest request){
        ResponseMap result = new ResponseMap();

        String refreshToken = authRefreshTokenMapper.findByAccessToken(getNewAccessToken.getAccessToken());

        // AccessToken은 만료되었지만 RefreshToken은 만료되지 않은 경우
        if(jwtProvider.validateJwtToken(request, refreshToken)){
            String userId = jwtProvider.getUserInfo(refreshToken);
            String newAccessToken = jwtProvider.createJwtToken(userId);
            String newRefreshToken = jwtProvider.createRefreshToken(userId);

            authRefreshTokenMapper.insertRefreshToken(userId, newAccessToken, newRefreshToken);
            authRefreshTokenMapper.deleteByAccessToken(getNewAccessToken.getAccessToken());

            result.setResponseData("accessToken", newAccessToken);
            result.setResponseData("message", "새로운 토큰이 발급되었습니다.");
        }else{
            // RefreshToken 또한 만료된 경우는 로그인을 다시 진행해야 한다.
            result.setResponseData("code", ErrorType.ReLogin.getCode());
            result.setResponseData("message", ErrorType.ReLogin.getDescription());
            result.setResponseData("HttpStatus", ErrorType.ReLogin.getHttpStatus());
        }
        return result;
    }
}
