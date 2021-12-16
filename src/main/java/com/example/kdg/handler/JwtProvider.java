package com.example.kdg.handler;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.dto.auth.AuthDto;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    private final String secretKey ="com.kdg.SecretKey";
    private long accessExpireTime = (60 * 60 * 1000L) * 3;
    private long refreshExpireTime =  ((60 * 60 * 1000L) * 24) * 60;
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String createJwtToken(AuthDto authDto) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "JWT");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("account", authDto.getUserId());

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + accessExpireTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("account") // 토큰 용도
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    public String createRefreshToken(AuthDto authDto) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("account", authDto.getUserId());

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + refreshExpireTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("User Checking") // 토큰 용도
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("위조된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
