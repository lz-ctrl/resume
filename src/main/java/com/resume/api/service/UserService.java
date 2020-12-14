package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.UserMapper;
import com.resume.api.dto.UserDto;
import com.resume.api.entity.User;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import com.resume.api.utils.ConstantUtils;
import com.resume.api.utils.JwtUtil;
import com.resume.api.utils.WxUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;


/**
 * @author lz
 */
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User findUserByOpenId(String openId){
        return userMapper.findUserByOpenId(openId);
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
            //小程序不用密码登录，设置默认密码为123
            user.setPassword(passwordEncoder.encode("123"));
            userMapper.insert(user);
            return user;
        }else{
            //代表用户存在，返回用户
            user=userMapper.selectList(new EntityWrapper<User>().eq("open_id",user.getOpenId())).get(0);
        }
        //生成token返回给前端
        LinkedList<GrantedAuthority> linkedList = new LinkedList<>();
        Date expireDate = new Date(System.currentTimeMillis() + ConstantUtils.TOKEN_EXPIRE_TIME);
        linkedList.add(new SimpleGrantedAuthority("ROLE_USER"));
        String token = ConstantUtils.TOKEN_PREFIX + JwtUtil.sign(user.getId(), user.getOpenId(),
                                                                 user.getPassword(), expireDate, linkedList);
        user.setToken(token);
        user.setTokenTime(expireDate);
        return user;
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
