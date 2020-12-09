package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lz
 */
@Data
public class PageVo {
    @ApiModelProperty("查询出的所有数据")
    List<?> list;
    @ApiModelProperty("总条数")
    Integer count;
}
