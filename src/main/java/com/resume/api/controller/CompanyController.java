package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.CompanyDto;
import com.resume.api.service.CompanyService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.CompanyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 */
@Api(tags = "公司信息接口")
@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @ApiOperation(value = "新增公司信息",notes = "新增公司信息(需要传简历id)")
    @PostMapping()
    public RestApiResult<CompanyVo> create(@RequestBody @Validated CompanyDto companyDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(companyService.create(companyDto), CompanyVo.class));
    }

    @ApiOperation(value = "修改公司信息",notes = "修改公司信息")
    @PutMapping()
    public RestApiResult<CompanyVo> update(@RequestBody @Validated CompanyDto companyDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(companyService.update(companyDto), CompanyVo.class));
    }

    @ApiOperation(value = "删除公司信息",notes = "删除公司信息")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        companyService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询公司信息",notes = "根据id查询公司信息")
    @GetMapping("{id}")
    public RestApiResult<CompanyVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(companyService.get(id), CompanyVo.class));
    }

}
