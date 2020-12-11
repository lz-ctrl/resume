package com.resume.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class CompanyDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("担任职务")
    private String post;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM")
    @ApiModelProperty("公司开始时间")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM")
    @ApiModelProperty("公司结束时间")
    private Date endTime;
    @ApiModelProperty("简历id(新增必传)")
    private Integer resumeId;
    @ApiModelProperty("用户id(新增必传)")
    private Integer userId;
}
