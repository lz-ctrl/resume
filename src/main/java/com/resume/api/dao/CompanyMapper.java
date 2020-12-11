package com.resume.api.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.resume.api.entity.Company;
import com.resume.api.entity.Job;
import com.resume.api.entity.Position;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lz
 */
public interface CompanyMapper extends BaseMapper<Company> {

    /**
     * 查询所有职位
     * @return
     */
    List<Position> findAllPosition();

    /**
     * 根据职位id查询所有子职位
     * @param positionId
     * @return
     */
    List<Job> findListByPositionId(@Param(value = "positionId") Integer positionId);
}
