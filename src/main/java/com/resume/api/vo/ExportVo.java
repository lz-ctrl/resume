package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lz
 */
@Data
public class ExportVo {
    @ApiModelProperty("PDF或者WORD的下载路径")
    private String path;
    @ApiModelProperty("图片的下载路径(一页一张图片)")
    private List<String> imgPath;
}
