package com.example.kdg.mapper;

import com.example.kdg.dto.account.AccountDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    void insertAccount(AccountDTO accountDTO);
}
