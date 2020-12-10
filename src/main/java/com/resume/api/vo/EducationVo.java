package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class EducationVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("活动名称/经历名称")
    private String activityName;
    @ApiModelProperty("活动角色/经历角色/担任人")
    private String activityRole;
    @ApiModelProperty("内容 用<li>隔开</li>")
    private String content;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("所属简历id")
    private Integer resumeId;
    @ApiModelProperty("所属学校id")
    private Integer schoolId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("经历开始时间")
    private Date startTime;
    @ApiModelProperty("经历结束时间")
    private Date endTime;
}
