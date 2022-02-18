package com.example.kdg.app;

import com.example.kdg.dto.notice.NoticeAddDto;
import com.example.kdg.dto.notice.NoticeUpdateDto;
import com.example.kdg.response.ApiResponse;
import com.example.kdg.response.ResponseMap;
import com.example.kdg.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Notice")
@RequestMapping("/app/notice")
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/lists")
    @ApiOperation(value = "목록")
    public ApiResponse getFindAllNoticeList(){
        return noticeService.getFindAllNoticeList();
    }

    @PostMapping("add")
    @ApiOperation(value = "등록")
    public ApiResponse addNotice(@Valid NoticeAddDto noticeAddDto){
        return noticeService.addNotice(noticeAddDto);
    }

    @PutMapping("update")
    @ApiOperation(value = "수정")
    public ApiResponse updateNotice(@Valid NoticeUpdateDto noticeUpdateDto){
        return noticeService.updateNotice(noticeUpdateDto);
    }

    @DeleteMapping("delete/{noticeNo}")
    @ApiOperation(value = "삭제")
    public ApiResponse deleteNotice(@PathVariable("noticeNo") int noticeNo){
        ResponseMap result = new ResponseMap();

        noticeService.deleteNotice(noticeNo);
        result.setResponseData("noticeDeleteResult", noticeNo);

        return result;
    }
}
