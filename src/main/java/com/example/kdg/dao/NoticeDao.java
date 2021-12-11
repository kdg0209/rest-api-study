package com.example.kdg.dao;

import lombok.Data;

@Data
public class NoticeDao {

    private long NoticeNo;
    private int NoticeStatus;
    private int kind;
    private int targetCount;
    private String title;
    private String content;
    private int registerId;
    private String registerDate;
    private int updaterId;
    private String updaterDate;
    private int isDelete;
}
