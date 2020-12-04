package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.EducationDto;
import com.resume.api.entity.StudyLevel;
import com.resume.api.service.EducationService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.EducationVo;
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
@Api(tags = "教育经历接口")
@RestController
@RequestMapping("education")
public class EducationController {

    @Autowired
    EducationService educationService;

    @ApiOperation(value = "新增教育经历",notes = "新增教育经历")
    @PostMapping()
    public RestApiResult<EducationVo> create(@RequestBody @Validated EducationDto educationDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(educationService.create(educationDto), EducationVo.class));
    }

    @ApiOperation(value = "修改教育经历",notes = "修改教育经历")
    @PutMapping()
    public RestApiResult<EducationVo> update(@RequestBody @Validated EducationDto educationDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(educationService.update(educationDto), EducationVo.class));
    }

    @ApiOperation(value = "删除教育经历",notes = "删除教育经历")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        educationService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询教育经历",notes = "根据id查询教育经历")
    @GetMapping("{id}")
    public RestApiResult<EducationVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(educationService.get(id), EducationVo.class));
    }

    @ApiOperation(value = "查询所有学历",notes = "查询所有学历")
    @PostMapping("level")
    public RestApiResult<List<StudyLevel>> level(){
        return new RestApiResult<>(RestCode.SUCCESS, educationService.findStudyLevelAll());
    }


}
