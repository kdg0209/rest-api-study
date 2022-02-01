package com.example.kdg.common;

import com.example.kdg.dao.AuthDao;
import com.example.kdg.exception.ErrorType;
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
        AuthDao member = authMapper.findByAccountUserId(username);

        if (member == null) {
            throw new AuthenticationException(ErrorType.UsernameOrPasswordNotFoundException);
        } else {
            return member;
        }
    }
}
