package com.example.kdg.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetNewAccessToken {

    @NotBlank
    @ApiModelProperty(value = "기존 토큰 값", example = "test", required = true)
    private String accessToken;
}
