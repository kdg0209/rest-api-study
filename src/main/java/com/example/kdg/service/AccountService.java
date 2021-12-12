package com.example.kdg.service;

import com.example.kdg.dao.AccountDao;
import com.example.kdg.dto.account.AccountDto;
import com.example.kdg.mapper.AccountMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;

    public ApiResponse addAccount(AccountDto accountDto){
        ResponseMap result = new ResponseMap();

        AccountDao accountDao = new AccountDao();
        accountDao.setUserId(accountDto.getUserId());
        accountDao.setUserPwd(hashPassword(accountDto.getUserPwd()));
        accountDao.setUserName(accountDto.getUserName());
        accountDao.setUserAddr(accountDto.getUserAddr());
        accountDao.setUserAddr2(accountDto.getUserAddr2());
        accountDao.setUserTel(accountDto.getUserTel());
        accountDao.setUserGender(accountDto.getUserGender());
        accountDao.setUserBirthDay(accountDto.getUserBirthDay());
        accountDao.setUserChannel(accountDto.getUserChannel());
        accountDao.setUserPhotoUrl(accountDto.getUserPhotoUrl());
        accountDao.setUserMemo(accountDto.getUserMemo());

        accountMapper.insertAccount(accountDao);
        accountMapper.insertAccountInfo(accountDao);
        return result;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
