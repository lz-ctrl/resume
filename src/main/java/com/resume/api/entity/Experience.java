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
@TableName("tb_company_experience")
public class Experience {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String companyName;
    private String workName;
    private String content;
    private String achievement;
    private Integer userId;
    private Integer resumeId;
    private Integer companyId;
    private Date createTime;
}
