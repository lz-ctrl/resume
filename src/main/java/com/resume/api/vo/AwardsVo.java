package com.resume.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lz
 */
@Data
public class AwardsVo {
    private Integer id;
    @ApiModelProperty("内容")
    private String content;
    private Integer userId;
    private Integer resumeId;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("证书开始时间")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("证书结束时间")
    private Date endTime;
    private Date createTime;
}
