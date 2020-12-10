package com.resume.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class EducationDto {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("活动名称/经历名称")
    private String activityName;
    @ApiModelProperty("活动角色/经历角色/担任人")
    private String activityRole;
    @ApiModelProperty("内容 用<li>隔开</li>")
    private String content;
    @ApiModelProperty("所属用户id(新增必传)")
    private Integer userId;
    @ApiModelProperty("所属简历id(新增必传)")
    private Integer resumeId;
    @ApiModelProperty("所属学校id(新增必传)")
    private Integer schoolId;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("经历开始时间")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("经历结束时间")
    private Date endTime;
}
