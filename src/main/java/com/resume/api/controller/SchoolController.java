package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.SchoolDto;
import com.resume.api.entity.StudyLevel;
import com.resume.api.service.SchoolService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.SchoolVo;
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
@Api(tags = "教育(学校)信息接口")
@RestController
@RequestMapping("school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;

    @ApiOperation(value = "新增教育信息",notes = "新增教育信息(需要传简历id)")
    @PostMapping()
    public RestApiResult<SchoolVo> create(@RequestBody @Validated SchoolDto schoolDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(schoolService.create(schoolDto), SchoolVo.class));
    }

    @ApiOperation(value = "修改教育信息",notes = "修改教育信息")
    @PutMapping()
    public RestApiResult<SchoolVo> update(@RequestBody @Validated SchoolDto schoolDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(schoolService.update(schoolDto), SchoolVo.class));
    }

    @ApiOperation(value = "删除教育信息",notes = "删除教育信息")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        schoolService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询教育信息",notes = "根据id查询教育信息")
    @GetMapping("{id}")
    public RestApiResult<SchoolVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(schoolService.get(id), SchoolVo.class));
    }

    @ApiOperation(value = "查询所有学历",notes = "查询所有学历(对应studyLevelId)")
    @PostMapping("level")
    public RestApiResult<List<StudyLevel>> level(){
        return new RestApiResult<>(RestCode.SUCCESS, schoolService.findStudyLevelAll());
    }
}
