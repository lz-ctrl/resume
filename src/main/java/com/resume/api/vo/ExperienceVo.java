package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class ExperienceVo {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("公司名称")
    private String companyName;
    @ApiModelProperty("职业名称")
    private String post;
    @ApiModelProperty("职业名称")
    private Date startTime;
    @ApiModelProperty("职业名称")
    private Date endTime;
    @ApiModelProperty("工作内容 用<li>隔开</li>")
    private String content;
    @ApiModelProperty("工作业绩 用<li>隔开</li>")
    private String achievement;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("所属简历id")
    private Integer resumeId;
    @ApiModelProperty("所属公司id")
    private Integer companyId;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
