package com.example.kdg.handler;

import com.example.kdg.common.CustomUserDetailService;
import com.example.kdg.dto.auth.AuthDTO;
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

    public String createAccessToken(AuthDTO authDTO) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", authDTO.getEmail());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

        String jwt = Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwt;
    }

    public String createRefreshToken(AuthDTO authDTO) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", authDTO.getEmail());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);

        String jwt = Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwt;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserInfo(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
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
