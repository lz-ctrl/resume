package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.UserMapper;
import com.resume.api.dto.EducationDto;
import com.resume.api.dto.UserDto;
import com.resume.api.entity.Education;
import com.resume.api.entity.User;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import com.resume.api.utils.WxUtil;
import org.springframework.stereotype.Service;


/**
 * @author lz
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserByPhone(String phone){
        return null;
    }

    /**
     * 用户登录 注册
     * @param userDto
     * @return
     */
    public User userLogin(UserDto userDto){
        if(userDto==null||userDto.getCode()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "code不能为空");
        }
        String openId= WxUtil.getOpenIdByCode(userDto.getCode());
        if(openId==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403,"openId获取失败");
        }
        User user=new User();
        BeanUtil.copyProperties(userDto, user);
        user.setOpenId(openId);
        if(userMapper.selectList(new EntityWrapper<User>().eq("open_id",user.getOpenId())).size()<=0){
            //代表用户不存在，添加用户
            userMapper.insert(user);
            return user;
        }else{
            //代表用户存在，返回用户
            return userMapper.selectList(new EntityWrapper<User>().eq("open_id",user.getOpenId())).get(0);
        }

    }

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    public User update(UserDto userDto){
        if(userDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        User user=new User();
        BeanUtil.copyProperties(userDto, user);
        userMapper.updateById(user);
        return user;
    }
}
