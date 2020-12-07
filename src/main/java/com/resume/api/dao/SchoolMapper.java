package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.School;
import java.util.List;

/**
 * @author lz
 */
public interface SchoolMapper extends BaseMapper<School> {

    /**
     * 根据简历id查询学校
     * @param resumeId
     * @return
     */
    List<School> findByResumeIdLevel(Integer resumeId);
}
