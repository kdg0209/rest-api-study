package com.example.kdg.dto.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class NoticeAddDto {

    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "상태 0:비활성화, 1:활성화", example = "1", required = true)
    private int noticeStatus;

    @Range(min = 0, max = 3)
    @ApiModelProperty(value = "분류(0:일반, 1:버전, 2:이벤트, 3:긴급)", example = "0", required = true)
    private int kind;

    @NotBlank
    @ApiModelProperty(value = "제목", example = "제목은...", required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(value = "내용", example = "내용은...", required = true)
    private String content;

    @ApiModelProperty(value = "아이디", example = "109", required = true)
    private int registerId;
}
