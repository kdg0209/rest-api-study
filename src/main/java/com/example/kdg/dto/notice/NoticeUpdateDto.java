package com.example.kdg.dto.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class NoticeUpdateDto {

    @ApiModelProperty(value = "해당 글의 번호", required = true, example = "1")
    private long noticeNo;

    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "상태 0:비활성화, 1:활성화", required = true, example = "1")
    private int noticeStatus;

    @Range(min = 0, max = 3)
    @ApiModelProperty(value = "분류(0:일반, 1:버전, 2:이벤트, 3:긴급)", required = true, example = "0")
    private int kind;

    @NotBlank
    @ApiModelProperty(value = "제목", example = "제목은...", required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "내용", example = "내용은...", required = true)
    private String content;

    @ApiModelProperty(value = "해당 글의 수정자", example = "109", required = true)
    private int updaterId;
}
