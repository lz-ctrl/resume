package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.Experience;
import com.resume.api.vo.ExperienceAllVo;

import java.util.List;

/**
 * @author lz
 */
public interface ExperienceMapper extends BaseMapper<Experience> {


    /**
     * 条件查询all
     * @param experience
     * @return
     */
     List<ExperienceAllVo> findAll(Experience experience);
}
