package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class UserVo {
    private Integer id;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户openId")
    private String openId;
    @ApiModelProperty("用户unionId")
    private String unionId;
    @ApiModelProperty("用户微信昵称")
    private String realName;
    @ApiModelProperty("用户生日")
    private Date birthdayTime;
    @ApiModelProperty("用户性别")
    private Integer gender;
    @ApiModelProperty("用户微信号")
    private String wxNum;
    @ApiModelProperty("用户邮箱")
    private String email;
}
