package com.example.kdg.controller;

import com.example.kdg.dto.account.AccountDto;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.service.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Account")
@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/add")
    public ApiResponse addAccount(@Valid AccountDto accountDto){
        return accountService.addAccount(accountDto);
    }
}
