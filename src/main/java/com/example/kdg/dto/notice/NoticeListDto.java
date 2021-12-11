package com.example.kdg.dto.notice;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NoticeListDto {

    private long noticeNo;
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
