package com.resume.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lz
 */
@Data
public class ExportDto {
    @ApiModelProperty("简历id")
    private Integer resumeId;
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("PDF PNG DOC")
    private String key;
    @ApiModelProperty("是否导出为邮箱 填1为导出")
    private Integer ifEmail;
    @ApiModelProperty("邮箱号(导出到邮箱时必填)")
    private String email;

}
