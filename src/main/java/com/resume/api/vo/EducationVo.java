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
    @ApiModelProperty("学校名称")
    private String schoolName;
    @ApiModelProperty("学历级别")
    private Integer studyLevelId;
    @ApiModelProperty("专业名称")
    private String major;
    @ApiModelProperty("学校开始时间")
    private Date schoolStartTime;
    @ApiModelProperty("学校结束时间")
    private Date schoolEndTime;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("所属简历id")
    private Integer resumeId;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
