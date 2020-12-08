package com.resume.api.vo;

import com.resume.api.entity.Company;
import com.resume.api.entity.Experience;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lz
 */
@Data
public class CompanyExperienceVo {
    @ApiModelProperty("公司信息")
    Company company;
    @ApiModelProperty("工作经历信息")
    Experience experience;
}
