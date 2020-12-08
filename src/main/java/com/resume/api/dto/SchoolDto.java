package com.resume.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class SchoolDto {

    private Integer id;
    @ApiModelProperty("学校名称")
    private String name;
    @ApiModelProperty("学校职务/干部")
    private String post;
    @ApiModelProperty("在校开始时间")
    private Date startTime;
    @ApiModelProperty("在校结束时间")
    private Date endTime;
    @ApiModelProperty("专业名称")
    private String major;
    @ApiModelProperty("简历id(新增必传)")
    private Integer resumeId;
}
