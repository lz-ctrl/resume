package com.resume.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lz
 */
@Data
public class TemplateDto {
    @ApiModelProperty("模板id")
    private Integer id;
    @ApiModelProperty("模板名称")
    private String name;
    @ApiModelProperty("模板内容")
    private String content;
    @ApiModelProperty("0-2 第几条")
    private Integer num;
}
