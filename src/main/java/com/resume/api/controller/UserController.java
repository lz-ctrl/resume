package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.ResumeDto;
import com.resume.api.dto.UserDto;
import com.resume.api.service.UserService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.vo.ResumeVo;
import com.resume.api.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录注册", notes = "用户不存在则注册(code必传)")
    @PostMapping("/login")
    public RestApiResult<UserVo> userLogin(@RequestBody @Validated UserDto userDto) {
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(userService.userLogin(userDto), UserVo.class));
    }

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息(id必传)")
    @PutMapping()
    public RestApiResult<UserVo> update(@RequestBody @Validated UserDto userDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(userService.update(userDto), UserVo.class));
    }
}
