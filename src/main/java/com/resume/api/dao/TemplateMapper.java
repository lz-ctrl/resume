package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.Template;
import org.springframework.data.repository.query.Param;

/**
 * @author lz
 */
public interface TemplateMapper extends BaseMapper<Template> {

    /**
     * 随机查询一条模板数据
     * @param name
     * @return
     */
    Template findRandByName(@Param("name") String name);

    /**
     * 根据条数查询一条模板数据
     * @param name
     * @param num
     * @return
     */
    Template findLimitByName(@Param("name") String name,@Param("num") Integer num);
}
