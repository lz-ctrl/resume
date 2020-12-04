package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.ResumeMapper;
import com.resume.api.dto.ResumeDto;
import com.resume.api.entity.Resume;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lz
 */
@Service
public class ResumeService {

    final ResumeMapper resumeMapper;

    public ResumeService(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    public String getData(){
        return null;
    }

    /**
     * 新增简历
     * @param resumeDto
     * @return
     */
    public Resume create(ResumeDto resumeDto){
        if(resumeDto.getUserId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403,"用户id不能为空");
        }
        Resume resume=new Resume();
        BeanUtil.copyProperties(resumeDto, resume);
        resume.setCreateTime(new Date());
        resumeMapper.insert(resume);
        return resume;
    }

    /**
     * 修改简历
     * @param resumeDto
     * @return
     */
    public Resume update(ResumeDto resumeDto){
        if(resumeDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Resume resume=new Resume();
        BeanUtil.copyProperties(resumeDto, resume);
        resumeMapper.updateById(resume);
        return resume;
    }

    /**
     * 删除简历
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return resumeMapper.deleteById(id);
    }
}
