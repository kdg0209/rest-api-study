package com.example.kdg.service;

import com.example.kdg.dao.NoticeDao;
import com.example.kdg.dto.notice.NoticeAddDto;
import com.example.kdg.dto.notice.NoticeUpdateDto;
import com.example.kdg.mapper.NoticeMapper;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NoticeMapper noticeMapper;

    public ApiResponse getFindAllNoticeList(){
        ResponseMap result = new ResponseMap();
        result.setResponseData("noticeList", noticeMapper.findAllNoticeList());
        return result;
    }

    public ApiResponse addNotice(NoticeAddDto noticeAddDto) {
        Map<String, Object> map = new HashMap<>();

        NoticeDao noticeDao = new NoticeDao();
        noticeDao.setNoticeStatus(noticeAddDto.getNoticeStatus());
        noticeDao.setKind(noticeAddDto.getKind());
        noticeDao.setTitle(noticeAddDto.getTitle());
        noticeDao.setContent(noticeAddDto.getContent());
        noticeDao.setRegisterId(noticeAddDto.getRegisterId());

        noticeMapper.insertNotice(noticeDao);

        map.put("addNotice", noticeAddDto);
        ApiResponse result = new ApiResponse(map);
        return result;
    }

    public ApiResponse updateNotice(NoticeUpdateDto noticeUpdateDto){
        ResponseMap result = new ResponseMap();

        NoticeDao noticeDao = new NoticeDao();
        noticeDao.setNoticeNo(noticeUpdateDto.getNoticeNo());
        noticeDao.setNoticeStatus(noticeUpdateDto.getNoticeStatus());
        noticeDao.setKind(noticeUpdateDto.getKind());
        noticeDao.setTitle(noticeUpdateDto.getTitle());
        noticeDao.setContent(noticeUpdateDto.getContent());
        noticeDao.setUpdaterId(noticeUpdateDto.getUpdaterId());

        noticeMapper.updateNotice(noticeDao);

        result.setResponseData("updateNotice", noticeUpdateDto);

       return result;
    }

    public ApiResponse deleteNotice(int noticeNo){
        ResponseMap result = new ResponseMap();
        noticeMapper.deleteByNoticeNo(noticeNo);

        result.setResponseData("deleteNotice", noticeNo);
        return result;
    }
}
