package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.TemplateDto;
import com.resume.api.entity.Template;
import com.resume.api.exception.ServiceException;
import com.resume.api.service.TemplateService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.TemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author lz
 */
@Api(tags = "模板接口")
@RestController
@RequestMapping("template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @ApiOperation(value = "根据名称随机查询一条模板",notes = "根据名称随机查询一条模板")
    @PostMapping("rand")
    public RestApiResult<TemplateVo> rand(@RequestBody @Validated TemplateDto templateDto){
        if(templateDto==null||templateDto.getName()==null){
            throw new ServiceException(RestCode.VALID_ERROR_100, "模板名称不能为空");
        }
        Template template=templateService.findRandByName(templateDto.getName());
        if(template==null){
            throw new ServiceException(RestCode.BAD_REQUEST_416,"没要查询到您要的语料信息");
        }
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(template, TemplateVo.class));
    }

    @ApiOperation(value = "根据名称按照条数查询一条模板",notes = "根据名称按照条数查询一条模板")
    @PostMapping("order")
    public RestApiResult<TemplateVo> order(@RequestBody @Validated TemplateDto templateDto){
        if(templateDto==null||templateDto.getName()==null){
            throw new ServiceException(RestCode.VALID_ERROR_100, "模板名称不能为空");
        }
        if(templateDto==null||templateDto.getNum()==null){
            throw new ServiceException(RestCode.VALID_ERROR_100, "条数不能为空");
        }
        Template template=templateService.findLimitByName(templateDto.getName(),templateDto.getNum());
        if(template==null){
            throw new ServiceException(RestCode.BAD_REQUEST_416,"没要查询到您要的语料信息");
        }
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(template, TemplateVo.class));
    }
}
