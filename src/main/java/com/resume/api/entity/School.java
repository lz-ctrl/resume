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
@TableName("tb_school")
public class School {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String post;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private String major;
    private Integer resumeId;
    private Integer studyLevelId;

    /**
     * 学历名称
     */
    @TableField(exist = false)
    private String studyLevelName;
}
