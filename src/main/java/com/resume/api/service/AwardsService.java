package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.AwardsMapper;
import com.resume.api.dto.AwardsDto;
import com.resume.api.entity.Awards;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lz
 */
@Service
public class AwardsService {

    private final AwardsMapper awardsMapper;

    public AwardsService(AwardsMapper awardsMapper) {
        this.awardsMapper = awardsMapper;
    }

    /**
     * 新增获奖证书
     * @param awardsDto
     * @return
     */
    public Awards create(AwardsDto awardsDto){
        if(awardsDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        Awards awards=new Awards();
        BeanUtil.copyProperties(awardsDto, awards);
        awards.setCreateTime(new Date());
        awardsMapper.insert(awards);
        return awards;
    }


    /**
     * 修改获奖证书
     * @param awardsDto
     * @return
     */
    public Awards update(AwardsDto awardsDto){
        if(awardsDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Awards awards=new Awards();
        BeanUtil.copyProperties(awardsDto, awards);
        awardsMapper.updateById(awards);
        return awards;
    }

    /**
     * 根据id查询获奖证书
     * @param id
     * @return
     */
    public Awards get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Awards awards= awardsMapper.selectById(id);
        return awards;
    }
    /**
     * 根据简历id查询获奖证书
     * @param resumeId
     * @return
     */
    public List<Awards> list(Integer resumeId){
        return awardsMapper.selectList(new EntityWrapper<Awards>().eq("resume_id", resumeId));
    }

    /**
     * 删除获奖证书
     * @param id
     * @return
     */
    public Integer delete(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        return awardsMapper.deleteById(id);
    }
}
