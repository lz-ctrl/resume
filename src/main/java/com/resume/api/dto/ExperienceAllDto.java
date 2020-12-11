package com.resume.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class ExperienceAllDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("职业名称")
    private String post;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM")
    @ApiModelProperty("公司开始时间")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM")
    @ApiModelProperty("公司结束时间")
    private Date endTime;
    @ApiModelProperty("工作内容 用<li>隔开</li>")
    private String content;
    @ApiModelProperty("工作业绩 用<li>隔开</li>")
    private String achievement;
    @ApiModelProperty("所属用户id(新增必传)")
    private Integer userId;
    @ApiModelProperty("所属简历id(新增必传)")
    private Integer resumeId;
}
