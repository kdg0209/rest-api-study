package com.example.kdg.app;

import com.example.kdg.dto.auth.AuthDTO;
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
@RequestMapping("/app/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid AuthDTO authDto) {
        return authService.login(authDto);
    }

    //    @PostMapping("/get-newToken")
//    public ApiResponse newAccessToken(@RequestBody @Valid GetNewAccessToken getNewAccessToken, HttpServletRequest request) {
//        return authService.newAccessToken(getNewAccessToken, request);
//    }
}
