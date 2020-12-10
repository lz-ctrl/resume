package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.dto.EducationDto;
import com.resume.api.entity.Education;

import java.util.List;

/**
 * @author lz
 */
public interface EducationMapper extends BaseMapper<Education> {

    /**
     * 根据 用户 简历 学习id查询
     * @param education
     * @return
     */
     List<Education> findAll(Education education);
}
