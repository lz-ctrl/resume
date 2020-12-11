package com.resume.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author lz
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 返回的token
     */
    @TableField(exist = false)
    private String token;

    /**
     * token过期时间
     */
    @TableField(exist = false)
    private Date tokenTime;
}
