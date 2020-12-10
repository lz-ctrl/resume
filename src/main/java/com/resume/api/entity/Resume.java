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
@TableName("tb_resume")
public class Resume {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String resumeName;
    private String headImg;
    private String expect;
    private String salary;
    private Date birthdayTime;
    private Integer gender;
    private String emailWx;
    private String phone;
    private String awards;
    private String interest;
    private Integer userId;
    private Date createTime;

    /**
     * 页数
     */
    @TableField(exist = false)
    private Integer page;

    /**
     * 条数
     */
    @TableField(exist = false)
    private Integer size;


}
