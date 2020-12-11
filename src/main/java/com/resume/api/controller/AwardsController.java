package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.AwardsDto;
import com.resume.api.exception.ServiceException;
import com.resume.api.service.AwardsService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.AwardsVo;
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
@Api(tags = "获奖证书")
@RestController
@RequestMapping("awards")
public class AwardsController {

    @Autowired
    AwardsService awardsService;

    @ApiOperation(value = "新增获奖证书",notes = "新增个获奖证书(需要传简历id)")
    @PostMapping()
    public RestApiResult<AwardsVo> create(@RequestBody @Validated AwardsDto awardsDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(awardsService.create(awardsDto), AwardsVo.class));
    }

    @ApiOperation(value = "修改获奖证书",notes = "修改获奖证书")
    @PutMapping()
    public RestApiResult<AwardsVo> update(@RequestBody @Validated AwardsDto awardsDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(awardsService.update(awardsDto), AwardsVo.class));
    }

    @ApiOperation(value = "删除获奖证书",notes = "删除获奖证书")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        awardsService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }

    @ApiOperation(value = "根据id查询获奖证书",notes = "根据id查询获奖证书")
    @GetMapping("{id}")
    public RestApiResult<AwardsVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(awardsService.get(id), AwardsVo.class));
    }

    @ApiOperation(value = "根据简历id查询获奖证书列表",notes = "根据简历id查询获奖证书列表")
    @PostMapping("list")
    public RestApiResult<List<AwardsVo>> list(@RequestBody @Validated AwardsDto awardsDto) throws ServiceException {
        if(awardsDto==null||awardsDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.mapList(awardsService.list(awardsDto.getResumeId()), AwardsVo.class));
    }
}
