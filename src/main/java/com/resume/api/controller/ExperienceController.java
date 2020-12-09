package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.ExperienceAllDto;
import com.resume.api.dto.ExperienceDto;
import com.resume.api.service.ExperienceService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.CompanyExperienceVo;
import com.resume.api.vo.ExperienceAllVo;
import com.resume.api.vo.ExperienceVo;
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

import java.util.List;

/**
 * @author lz
 */
@Api(tags = "工作经历接口")
@RestController
@RequestMapping("experience")
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @ApiOperation(value = "新增工作经历(同时创建公司信息)",notes = "新增工作经历(同时创建公司信息)")
    @PostMapping("all")
    public RestApiResult<CompanyExperienceVo> create(@RequestBody @Validated ExperienceAllDto experienceAllDto){
        return new RestApiResult<>(RestCode.SUCCESS, experienceService.createAll(experienceAllDto));
    }

    @ApiOperation(value = "新增工作经历(单独添加,需要传companyId)",notes = "新增工作经历(单独添加,需要传companyId)")
    @PostMapping()
    public RestApiResult<ExperienceVo> create(@RequestBody @Validated ExperienceDto experienceDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(experienceService.create(experienceDto), ExperienceVo.class));
    }

    @ApiOperation(value = "修改工作经历",notes = "修改工作经历")
    @PutMapping()
    public RestApiResult<ExperienceVo> update(@RequestBody @Validated ExperienceDto experienceDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(experienceService.update(experienceDto), ExperienceVo.class));
    }

    @ApiOperation(value = "删除工作经历",notes = "删除工作经历")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        experienceService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询工作经历",notes = "根据id查询工作经历")
    @GetMapping("{id}")
    public RestApiResult<ExperienceVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(experienceService.get(id), ExperienceVo.class));
    }

    @ApiOperation(value = "根据简历id或者公司id查询工作经历list",notes = "根据简历id或者公司id查询工作经历list(用户id必传)")
    @PostMapping("list")
    public RestApiResult<List<ExperienceAllVo>> list(@RequestBody @Validated ExperienceDto experienceDto){
        return new RestApiResult<>(RestCode.SUCCESS,experienceService.list(experienceDto));
    }

}
