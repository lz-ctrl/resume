package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.ResumeDto;
import com.resume.api.service.ResumeService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.ResumeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 */
@Api(tags = "简历接口")
@RestController
@RequestMapping("resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @ApiOperation(value = "新增简历",notes = "新增简历")
    @PostMapping()
    public RestApiResult<ResumeVo> create(@RequestBody @Validated ResumeDto resumeDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(resumeService.create(resumeDto), ResumeVo.class));
    }

    @ApiOperation(value = "修改简历",notes = "修改简历")
    @PutMapping()
    public RestApiResult<ResumeVo> update(@RequestBody @Validated ResumeDto resumeDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(resumeService.update(resumeDto), ResumeVo.class));
    }

    @ApiOperation(value = "删除简历",notes = "删除简历")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        resumeService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }


}
