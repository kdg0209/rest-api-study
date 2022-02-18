package com.example.kdg.service;

import com.example.kdg.dto.auth.AuthDTO;
import com.example.kdg.exception.ErrorCode;
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

    public ApiResponse login(AuthDTO authDTO) {
        ResponseMap result = new ResponseMap();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword())
            );

            String accessToken = jwtProvider.createAccessToken(authDTO);
            String refreshToken = jwtProvider.createRefreshToken(authDTO);

            result.setResponseData("accessToken", accessToken);
        } catch (Exception e) {
            throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
        }

        return result;
    }

//    public ApiResponse newAccessToken(GetNewAccessToken getNewAccessToken, HttpServletRequest request){
//        ResponseMap result = new ResponseMap();
//
//        String refreshToken = authRefreshTokenMapper.findByAccessToken(getNewAccessToken.getAccessToken());
//
//        // AccessToken은 만료되었지만 RefreshToken은 만료되지 않은 경우
//        if(jwtProvider.validateJwtToken(request, refreshToken)){
//            String userId = jwtProvider.getUserInfo(refreshToken);
//            String newAccessToken = jwtProvider.createJwtToken(userId);
//            String newRefreshToken = jwtProvider.createRefreshToken(userId);
//
//            authRefreshTokenMapper.insertRefreshToken(userId, newAccessToken, newRefreshToken);
//            authRefreshTokenMapper.deleteByAccessToken(getNewAccessToken.getAccessToken());
//
//            result.setResponseData("accessToken", newAccessToken);
//            result.setResponseData("message", "새로운 토큰이 발급되었습니다.");
//        }else{
//            // RefreshToken 또한 만료된 경우는 로그인을 다시 진행해야 한다.
//            result.setResponseData("code", ErrorCode.ReLogin.getCode());
//            result.setResponseData("message", ErrorCode.ReLogin.getDescription());
//            result.setResponseData("HttpStatus", ErrorCode.ReLogin.getHttpStatus());
//        }
//        return result;
//    }
}
