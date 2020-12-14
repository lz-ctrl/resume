package com.resume.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lz
 * 用来判断一些简历信息是否以及填完
 */
@Data
public class IfResumeVo {
    @ApiModelProperty("头像 0未填写 1已填写")
    private Integer ifHeadImg;
    @ApiModelProperty("求职期望 0未填写 1已填写")
    private Integer ifExpect;
    @ApiModelProperty("在校经历 0未填写 1已填写")
    private Integer ifEducation;
    @ApiModelProperty("证书/获奖 0未填写 1已填写")
    private Integer ifAwards;
    @ApiModelProperty("个人兴趣爱好 0未填写 1已填写")
    private Integer ifInterest;
}
