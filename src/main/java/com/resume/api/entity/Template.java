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
@TableName("tb_template")
public class Template {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String content;
    private Date createTime;

    /**
     * 统计数量
     */
    @TableField(exist = false)
    private Integer count;
}
