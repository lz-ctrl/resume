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
    @ApiModelProperty("头像base64")
    private String headImg;
    @ApiModelProperty("求职期望")
    private String expect;
    @ApiModelProperty("期望薪水")
    private String salary;
    @ApiModelProperty("获奖证书")
    private String awards;
    @ApiModelProperty("兴趣爱好")
    private String interest;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("生日")
    private Date birthdayTime;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("邮箱或微信号")
    private String emailWx;
    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("当前页")
    private Integer page;
    @ApiModelProperty("每页条数")
    private Integer size;
}
