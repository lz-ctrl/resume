package com.resume.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class ExperienceDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("工作内容 用<li>隔开</li>")
    private String content;
    @ApiModelProperty("工作业绩 用<li>隔开</li>")
    private String achievement;
    @ApiModelProperty("所属用户id(新增必传)")
    private Integer userId;
    @ApiModelProperty("所属简历id(新增必传)")
    private Integer resumeId;
    @ApiModelProperty("所属公司id(新增必传)")
    private Integer companyId;

}
