package com.example.kdg.common;

import com.example.kdg.dao.User;
import com.example.kdg.exception.ErrorCode;
import com.example.kdg.exception.customerException.AuthenticationException;
import com.example.kdg.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authMapper.findByEmail(username);

        if(user == null){
            throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
        }

        return user;
    }
}
