package com.example.kdg.dao;

import lombok.Data;

@Data
public class AccountDao {

    private long uid;
    private String userId;
    private String userPwd;
    private String role_code;
    private int userStatus;
    private String userName;
    private String userAddr;
    private String userAddr2;
    private String userTel;
    private String userGender;
    private String userBirthDay;
    private String userChannel;
    private String userPhotoUrl;
    private String userMemo;
}
