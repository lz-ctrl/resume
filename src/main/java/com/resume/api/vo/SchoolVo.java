package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class SchoolVo {
    @ApiModelProperty("返回的学校id")
    private Integer id;
    @ApiModelProperty("学校名称")
    private String name;
    @ApiModelProperty("学校职务/干部")
    private String post;
    @ApiModelProperty("在校开始时间")
    private Date startTime;
    @ApiModelProperty("在校结束时间")
    private Date endTime;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("专业名称")
    private String major;
    @ApiModelProperty("简历id")
    private Integer resumeId;
}
