package com.resume.api.security;

import com.resume.api.entity.User;
import com.resume.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * @author XiaoWeiBiao
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;


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
     * 只支持手机号码登录
     *
     * @param phone
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        if (NONE_USERNAME.equals(phone)) {
            throw new BadCredentialsException("手机号为空");
        }
        User userEntity = userService.findUserByPhone(phone);

        if (userEntity == null) {
            throw new BadCredentialsException("账号不存在");
        }
        LinkedList<GrantedAuthority> linkedList = new LinkedList<>();

        return new SecurityUser(userEntity.getId(), userEntity.getPhone(), userEntity.getPassword(), linkedList);

    }
}
