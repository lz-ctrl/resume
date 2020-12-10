package com.resume.api.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lz
 */
@Data
public class AwardsDto {
    private Integer id;
    @ApiModelProperty("内容")
    private String content;
    private Integer userId;
    private Integer resumeId;
}
