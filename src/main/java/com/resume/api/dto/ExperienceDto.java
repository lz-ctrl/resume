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
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("职业名称")
    private String workName;
    @ApiModelProperty("工作内容")
    private String companyContent;
    @ApiModelProperty("工作开始时间")
    private Date companyStartTime;
    @ApiModelProperty("工作结束时间")
    private Date companyEndTime;
    @ApiModelProperty("工作业绩")
    private String achievement;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("所属简历id")
    private Integer resumeId;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
