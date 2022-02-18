package com.example.kdg.admin;

import com.example.kdg.response.ApiResponse;
import com.example.kdg.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Notice")
@RequestMapping("/admin/notice")
@RestController
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping("/lists")
    @ApiOperation(value = "목록")
    public ApiResponse getFindAllNoticeList(){
        return noticeService.getFindAllNoticeList();
    }
}
