package com.example.kdg.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthDto {

    @NotBlank
    @ApiModelProperty(value = "아이디", example = "test", required = true)
    private String userId;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
    private String userPwd;
}
