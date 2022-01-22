package com.example.kdg.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface AuthRefreshTokenMapper {

    String findByAccessToken(@Param("accessToken") String accessToken);
    void insertRefreshToken(@Param("userId") String userId, @Param("accessToken") String accessToken, @Param("refreshToken") String refreshToken);
    void deleteByAccessToken(@Param("accessToken") String accessToken);
}
