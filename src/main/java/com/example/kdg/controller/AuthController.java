package com.example.kdg.controller;

import com.example.kdg.dto.auth.AuthDto;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.service.AuthService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Auth")
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid AuthDto authDto){
        return authService.login(authDto);
    }
}
