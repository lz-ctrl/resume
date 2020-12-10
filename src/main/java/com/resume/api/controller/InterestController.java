package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.InterestDto;
import com.resume.api.exception.ServiceException;
import com.resume.api.service.InterestService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.InterestVo;
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
@Api(tags = "个人兴趣爱好")
@RestController
@RequestMapping("interest")
public class InterestController {

    @Autowired
    InterestService interestService;

    @ApiOperation(value = "新增个人兴趣爱好",notes = "新增个人兴趣爱好(需要传简历id)")
    @PostMapping()
    public RestApiResult<InterestVo> create(@RequestBody @Validated InterestDto interestDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(interestService.create(interestDto), InterestVo.class));
    }

    @ApiOperation(value = "修改个人兴趣爱好",notes = "修改个人兴趣爱好")
    @PutMapping()
    public RestApiResult<InterestVo> update(@RequestBody @Validated InterestDto interestDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(interestService.update(interestDto), InterestVo.class));
    }

    @ApiOperation(value = "删除个人兴趣爱好",notes = "删除个人兴趣爱好")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        interestService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询个人兴趣爱好",notes = "根据id查询个人兴趣爱好")
    @GetMapping("{id}")
    public RestApiResult<InterestVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(interestService.get(id), InterestVo.class));
    }

    @ApiOperation(value = "根据简历ID查询个人兴趣爱好列表",notes = "根据简历ID查询个人兴趣爱好列表")
    @PostMapping("list")
    public RestApiResult<List<InterestVo>> list(@RequestBody @Validated InterestDto interestDto) throws ServiceException {
        if(interestDto==null||interestDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.mapList(interestService.list(interestDto.getResumeId()), InterestVo.class));
    }
}
