package com.example.kdg.app;

import com.example.kdg.dto.account.AccountDTO;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.service.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Account")
@RequestMapping("/app/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/acount")
    public ApiResponse acount(@RequestBody @Valid AccountDTO accountDto){
        return accountService.addAccount(accountDto);
    }
}
