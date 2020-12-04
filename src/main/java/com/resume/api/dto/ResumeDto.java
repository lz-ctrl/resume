package com.resume.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author lz
 */
@Data
public class ResumeDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("简历名称")
    private String resumeName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("求职期望")
    private String expect;
    @ApiModelProperty("期望薪水")
    private Double salary;
    @ApiModelProperty("其它经历")
    private String other;
    @ApiModelProperty("获奖证书")
    private String awards;
    @ApiModelProperty("兴趣爱好")
    private String interest;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
