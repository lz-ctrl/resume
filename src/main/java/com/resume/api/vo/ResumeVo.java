package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class ResumeVo {
    @ApiModelProperty("总数")
    private Integer id;
    @ApiModelProperty("简历名称")
    private String resumeName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("求职期望")
    private String expect;
    @ApiModelProperty("期望薪水")
    private Double salary;
    @ApiModelProperty("获奖证书")
    private String awards;
    @ApiModelProperty("兴趣爱好")
    private String interest;
    @ApiModelProperty("所属用户id")
    private Integer userId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("生日")
    private Date birthdayTime;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("邮箱或微信号")
    private String emailWx;
    @ApiModelProperty("电话")
    private String phone;
}
