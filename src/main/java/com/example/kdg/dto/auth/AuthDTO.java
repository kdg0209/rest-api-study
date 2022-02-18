package com.example.kdg.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AuthDTO {

    @NotBlank
    @ApiModelProperty(value = "아이디", example = "admin@naver.com", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", example = "12345", required = true)
    private String password;
}