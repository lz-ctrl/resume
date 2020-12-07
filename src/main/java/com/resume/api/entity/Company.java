package com.resume.api.entity;

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
@TableName("tb_company")
public class Company {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String post;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Integer resumeId;
}
