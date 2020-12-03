package com.resume.api.controller;

import com.resume.api.codec.RestApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lz
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("user")
public class UserController {

    @ApiOperation(value = "用户登录", notes = "需要调用获取token接口(user/token)")
    @PostMapping("/login")
    public RestApiResult userLogin( HttpServletRequest request) {
        System.out.println("进来了");
        return new RestApiResult();
    }
}
