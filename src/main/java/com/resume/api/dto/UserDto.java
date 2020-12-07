package com.resume.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class UserDto {
    private Integer id;
    private String phone;
    private String password;
    private String openId;
    private String unionId;
    private String realName;
    private Date birthdayTime;
    private Integer gender;
    private String wxNum;
    private String email;
    private Date createTime;
}
