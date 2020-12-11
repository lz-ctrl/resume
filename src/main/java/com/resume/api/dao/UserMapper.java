package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.User;

/**
 * @author lz
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
     User findUserByOpenId(String openId);
}
