package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.SchoolMapper;
import com.resume.api.dao.StudyLevelMapper;
import com.resume.api.dto.SchoolDto;
import com.resume.api.entity.School;
import com.resume.api.entity.StudyLevel;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lz
 */
@Service
public class SchoolService {

    private final SchoolMapper schoolMapper;
    private final StudyLevelMapper studyLevelMapper;

    public SchoolService(SchoolMapper schoolMapper, StudyLevelMapper studyLevelMapper) {
        this.schoolMapper = schoolMapper;
        this.studyLevelMapper = studyLevelMapper;
    }

    /**
     * 新增学校教育信息
     * @param schoolDto
     * @return
     */
    public School create(SchoolDto schoolDto){
        if(schoolDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        School school=new School();
        BeanUtil.copyProperties(schoolDto, school);
        school.setCreateTime(new Date());
        schoolMapper.insert(school);
        return school;
    }


    /**
     * 修改学校教育信息
     * @param schoolDto
     * @return
     */
    public School update(SchoolDto schoolDto){
        if(schoolDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        School school=new School();
        BeanUtil.copyProperties(schoolDto, school);
        schoolMapper.updateById(school);
        return school;
    }

    /**
     * 根据id查询学校教育信息
     * @param id
     * @return
     */
    public School get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        School school= schoolMapper.selectById(id);
        return school;
    }


    /**
     * 根据简历id查询教育信息
     * @param resumeId
     * @return
     */
    public List<School> list(Integer resumeId){
        return schoolMapper.findByResumeIdLevel(resumeId);
    }

    /**
     * 删除学校教育信息
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return schoolMapper.deleteById(id);
    }

    public List<StudyLevel> findStudyLevelAll(){
        return studyLevelMapper.selectList(new EntityWrapper<>());
    }
}
