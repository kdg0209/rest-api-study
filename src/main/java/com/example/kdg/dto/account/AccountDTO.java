package com.example.kdg.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class AccountDTO {

    @ApiModelProperty(hidden = true)
    private long idx;

    @NotBlank
    @ApiModelProperty(value = "아이디", example = "admin@naver.com", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", example = "12345", required = true)
    private String password;

    @NotBlank
    @ApiModelProperty(value = "사용자 권한", example = "ROLE_ADMIN", hidden = true)
    private String role;

    @NotBlank
    @ApiModelProperty(value = "사용자 이름", example = "홍길동", required = true)
    private String name;
}
