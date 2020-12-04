package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.Resume;

import java.util.List;

/**
 * @author lz
 */

public interface ResumeMapper extends BaseMapper<Resume> {


    /**
     * 多条件关联查询
     * @return
     */
    List<Resume> findResumeList();
}
