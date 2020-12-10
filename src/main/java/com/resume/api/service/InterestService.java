package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.InterestMapper;
import com.resume.api.dto.InterestDto;
import com.resume.api.entity.Interest;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lz
 */
@Service
public class InterestService {

    private final InterestMapper interestMapper;

    public InterestService(InterestMapper interestMapper) {
        this.interestMapper = interestMapper;
    }

    /**
     * 新增个人兴趣爱好
     * @param interestDto
     * @return
     */
    public Interest create(InterestDto interestDto){
        if(interestDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        Interest interest=new Interest();
        BeanUtil.copyProperties(interestDto, interest);
        interest.setCreateTime(new Date());
        interestMapper.insert(interest);
        return interest;
    }


    /**
     * 修改个人兴趣爱好
     * @param interestDto
     * @return
     */
    public Interest update(InterestDto interestDto){
        if(interestDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Interest interest=new Interest();
        BeanUtil.copyProperties(interestDto, interest);
        interestMapper.updateById(interest);
        return interest;
    }

    /**
     * 根据id查询个人兴趣爱好
     * @param id
     * @return
     */
    public Interest get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Interest interest= interestMapper.selectById(id);
        return interest;
    }
    /**
     * 根据简历id查询个人兴趣爱好
     * @param resumeId
     * @return
     */
    public List<Interest> list(Integer resumeId){
        return interestMapper.selectList(new EntityWrapper<Interest>().eq("resume_id",resumeId));
    }

    /**
     * 删除个人兴趣爱好
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return interestMapper.deleteById(id);
    }
}
