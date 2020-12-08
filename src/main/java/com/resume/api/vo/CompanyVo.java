package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class CompanyVo {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("担任职务")
    private String post;
    @ApiModelProperty("公司开始时间")
    private Date startTime;
    @ApiModelProperty("公司结束时间")
    private Date endTime;
    @ApiModelProperty("简历id(新增必传)")
    private Integer resumeId;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
