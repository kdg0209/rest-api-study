package com.example.kdg.service;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.handler.JwtProvider;
import com.example.kdg.mapper.AuthMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        AuthDao member = authMapper.findByAccountUserId(id);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(member != null) {
            authorities.add(new SimpleGrantedAuthority(member.getUserRole()));
            member.setAuthorities(authorities);
        }
        return member;
    }

    public ApiResponse login(AuthDto authDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthDao member = authMapper.findByAccountUserId(authDto.getUserId());

        if(!passwordEncoder.matches(authDto.getUserPwd(), member.getPassword())) {
            System.out.println("비밀번호 불일치");
        }

        String accessToken = jwtProvider.createJwtToken(authDto);
        String refreshToken = jwtProvider.createRefreshToken(authDto);

        ResponseMap result = new ResponseMap();
        result.setResponseData("accessToken", accessToken);
        result.setResponseData("refreshToken", refreshToken);

        return result;
    }
}
