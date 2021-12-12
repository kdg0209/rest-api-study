package com.example.kdg.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class AccountDto {

    @ApiModelProperty(hidden = true)
    private long uid;

    @NotBlank
    @ApiModelProperty(value = "아이디", example = "test", required = true)
    private String userId;

    @NotBlank
    @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
    private String userPwd;

    @ApiModelProperty(value = "사용자 권한", example = "000", hidden = true)
    private String roleCode;

    @ApiModelProperty(value = "사용자 상태", hidden = true)
    int userStatus = 1;

    @NotBlank
    @ApiModelProperty(value = "사용자 이름", example = "홍길동", required = true)
    private String userName;

    @Length(max = 40)
    @ApiModelProperty(value = "사용자 주소(len=40)", example = "서울시 화양동 어딘가", required = true)
    private String userAddr;

    @Length(max = 40)
    @ApiModelProperty(value = "사용자 상세주소(len=40)", example = "건대입구역")
    private String userAddr2;

    @Length(max = 15)
    @NotBlank
    @ApiModelProperty(value = "사용자 전화번호(len=15)", example = "01012345678", required = true)
    private String userTel;

    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "사용자 성별(0남자,1여자)", example = "0")
    private String userGender;

    @Length(max = 10)
    @ApiModelProperty(value = "사용자 생년월일(len=10)", example = "2020-01-01")
    private String userBirthDay;

    private String userChannel;

    @Length(max = 255)
    @ApiModelProperty(value = "사용자 이미지(len=255)", hidden = true)
    private String userPhotoUrl;

    @Length(max = 50)
    @ApiModelProperty(value = "사용자 메모(len=50)", example = "특이사항은...")
    private String userMemo;
}
