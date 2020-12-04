package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.EducationMapper;
import com.resume.api.dao.StudyLevelMapper;
import com.resume.api.dto.EducationDto;
import com.resume.api.entity.Education;
import com.resume.api.entity.StudyLevel;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lz
 */
@Service
public class EducationService {

    private final EducationMapper educationMapper;
    private final StudyLevelMapper studyLevelMapper;

    public EducationService(EducationMapper educationMapper, StudyLevelMapper studyLevelMapper) {
        this.educationMapper = educationMapper;
        this.studyLevelMapper = studyLevelMapper;
    }

    /**
     * 新增教育经历
     * @param educationDto
     * @return
     */
    public Education create(EducationDto educationDto){
        if(educationDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        Education education=new Education();
        BeanUtil.copyProperties(educationDto, education);
        educationMapper.insert(education);
        return education;
    }

    /**
     * 修改教育经历
     * @param educationDto
     * @return
     */
    public Education update(EducationDto educationDto){
        if(educationDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Education education=new Education();
        BeanUtil.copyProperties(educationDto, education);
        educationMapper.updateById(education);
        return education;
    }

    /**
     * 根据id查询教育经历
     * @param id
     * @return
     */
    public Education get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Education education= educationMapper.selectById(id);
        return education;
    }

    /**
     * 删除教育经历
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return educationMapper.deleteById(id);
    }

    public List<StudyLevel> findStudyLevelAll(){
        return studyLevelMapper.selectList(new EntityWrapper<StudyLevel>());
    }
}
