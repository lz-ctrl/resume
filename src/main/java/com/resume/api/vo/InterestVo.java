package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class InterestVo {
    private Integer id;
    @ApiModelProperty("内容")
    private String content;
    private Integer userId;
    private Integer resumeId;
    private Date createTime;
}
