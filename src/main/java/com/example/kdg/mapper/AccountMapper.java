package com.example.kdg.mapper;

import com.example.kdg.dao.AccountDao;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    void insertAccount(AccountDao accountDao);
    void insertAccountInfo(AccountDao accountDao);
}
