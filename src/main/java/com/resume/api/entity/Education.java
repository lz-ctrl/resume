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
@TableName("tb_education")
public class Education {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String schoolName;
    private Integer studyLevelId;
    private String major;
    private Date schoolStartTime;
    private Date schoolEndTime;
    private Integer userId;
    private Integer resumeId;
    private Date createTime;
}
