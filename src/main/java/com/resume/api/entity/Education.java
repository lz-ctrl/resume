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
@TableName("tb_school_education")
public class Education {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String activityName;
    private String activityRole;
    private String content;
    private Integer userId;
    private Integer resumeId;
    private Integer schoolId;
    private Date createTime;
    private Date startTime;
    private Date endTime;
}
