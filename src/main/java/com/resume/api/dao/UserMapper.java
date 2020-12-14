package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.User;
import org.springframework.data.repository.query.Param;

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

    /**
     * 更新获取的access_token
     * @param wxKey
     * @return
     */
     int updateWxKey(@Param("wxKey") String wxKey);
}
