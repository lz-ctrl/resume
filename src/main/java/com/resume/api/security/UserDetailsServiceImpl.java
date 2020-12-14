package com.resume.api.security;

import com.resume.api.entity.User;
import com.resume.api.service.UserService;
import com.resume.api.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * @author lz
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;


    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 更新用户更新时间锁
     */
    private final String USER_UPDATE_LOCK = "update_user_lock_";

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private final static String ROLE = "ROLE_USER";
    private final static String NONE_USERNAME = "NONE_PROVIDED";


    /**
     * 微信小程序
     * code获取openId登录
     * @param code
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        if (NONE_USERNAME.equals(code)) {
            throw new BadCredentialsException("code为空");
        }
        String openId= WxUtil.getOpenIdByCode(code);
        /*if(openId==null){
            throw new BadCredentialsException("openId获取失败");
        }*/
        User userEntity = userService.findUserByOpenId("123");
        if (userEntity == null) {
            throw new BadCredentialsException("账号不存在");
        }
        LinkedList<GrantedAuthority> linkedList = new LinkedList<>();
        /**
         * 没有多级权限管理,直接为默认用户
         */
        linkedList.add(new SimpleGrantedAuthority(ROLE));
        return new SecurityUser(userEntity.getId(), code, userEntity.getPassword(), linkedList);

    }
}
