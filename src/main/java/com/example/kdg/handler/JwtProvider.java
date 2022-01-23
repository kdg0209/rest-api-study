package com.example.kdg.handler;

import com.example.kdg.common.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String secretKey ="c88d74ba-1554-48a4-b549-b926f5d77c9e";
//    private long accessExpireTime = (60 * 60 * 1000L) * 3; // 3시간 후
    private final long accessExpireTime = 1 * 60 * 1000L;   // 1분
//    private long refreshExpireTime =  ((60 * 60 * 1000L) * 24) * 60; // 60일
    private final long refreshExpireTime = 1 * 60 * 2000L;   // 2분
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final CustomUserDetailService customUserDetailService;

    public String createJwtToken(String userId) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", userId);

        Date expiration = new Date(); // 토큰 만료 시간
        expiration.setTime(expiration.getTime() + accessExpireTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("User Checking") // 토큰 용도
                .setExpiration(expiration) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    public String createRefreshToken(String userId) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", userId);

        Date expiration = new Date(); // 토큰 만료 시간
        expiration.setTime(expiration.getTime() + refreshExpireTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("User Checking") // 토큰 용도
                .setExpiration(expiration) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserInfo(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId");
    }

    // Request의 Header에서 token 값을 가져옵니다. "token" : "token value'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }


    public boolean validateJwtToken(ServletRequest request, String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
            logger.error("위조된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
            logger.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
            logger.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
