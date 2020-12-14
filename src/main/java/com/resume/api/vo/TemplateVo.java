package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author lz
 */
@Data
public class TemplateVo {
    @ApiModelProperty("模板id")
    private Integer id;
    @ApiModelProperty("模板名称")
    private String name;
    @ApiModelProperty("模板内容")
    private String content;
    @ApiModelProperty("创建内容")
    private Date createTime;
    @ApiModelProperty("总条数")
    private Integer count;
}
