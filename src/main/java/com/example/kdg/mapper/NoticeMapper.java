package com.example.kdg.mapper;

import com.example.kdg.dao.NoticeDao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeDao> findAllNoticeList();

    void insertNotice(NoticeDao noticeDao);

    void updateNotice(NoticeDao noticeDao);

    void deleteByNoticeNo(int noticeNo);
}
