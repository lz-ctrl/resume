package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.ExperienceMapper;
import com.resume.api.dto.ExperienceDto;
import com.resume.api.entity.Experience;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lz
 */
@Service
public class ExperienceService {
    final ExperienceMapper experienceMapper;

    public ExperienceService(ExperienceMapper experienceMapper) {
        this.experienceMapper = experienceMapper;
    }

    /**
     * 新增 工作/实习 经历表
     * @param experienceDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Experience create(ExperienceDto experienceDto){
        if(experienceDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        Experience experience=new Experience();
        BeanUtil.copyProperties(experienceDto, experience);
        experienceMapper.insert(experience);
        return experience;
    }

    /**
     * 修改 工作/实习 经历表
     * @param experienceDto
     * @return
     */
    public Experience update(ExperienceDto experienceDto){
        if(experienceDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Experience experience=new Experience();
        BeanUtil.copyProperties(experienceDto, experience);
        experienceMapper.updateById(experience);
        return experience;
    }

    /**
     * 删除 工作/实习 经历表
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return experienceMapper.deleteById(id);
    }

    /**
     * 根据id查询 工作/实习 经历
     * @param id
     * @return
     */
    public Experience get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Experience experience= experienceMapper.selectById(id);
        return experience;
    }

}
