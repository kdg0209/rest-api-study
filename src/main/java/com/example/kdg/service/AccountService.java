package com.example.kdg.service;

import com.example.kdg.dto.account.AccountDTO;
import com.example.kdg.mapper.AccountMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;

    public ApiResponse addAccount(AccountDTO accountDTO){
        ResponseMap result = new ResponseMap();

        accountDTO.setPassword(hashPassword(accountDTO.getPassword()));
        accountMapper.insertAccount(accountDTO);
        return result;
    }

    private String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
