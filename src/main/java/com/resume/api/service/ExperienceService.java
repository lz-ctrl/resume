package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.CompanyMapper;
import com.resume.api.dao.ExperienceMapper;
import com.resume.api.dto.ExperienceAllDto;
import com.resume.api.dto.ExperienceDto;
import com.resume.api.entity.Company;
import com.resume.api.entity.Experience;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanMapper;
import com.resume.api.utils.BeanUtil;
import com.resume.api.vo.CompanyExperienceVo;
import com.resume.api.vo.ExperienceAllVo;
import com.resume.api.vo.ExperienceVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;


/**
 * @author lz
 */
@Service
public class ExperienceService {
    private final ExperienceMapper experienceMapper;
    private final CompanyMapper companyMapper;
    public ExperienceService(ExperienceMapper experienceMapper, CompanyMapper companyMapper) {
        this.experienceMapper = experienceMapper;
        this.companyMapper = companyMapper;
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
        if(experienceDto.getUserId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "用户id不能为空");
        }
        if(experienceDto.getCompanyId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "公司id不能为空");
        }
        Experience experience=new Experience();
        BeanUtil.copyProperties(experienceDto, experience);
        experienceMapper.insert(experience);
        return experience;
    }

    /**
     * 新增 工作/实习 经历 同时创建新公司信息
     * @param experienceAllDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CompanyExperienceVo createAll(ExperienceAllDto experienceAllDto){
        if(experienceAllDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        if(experienceAllDto.getUserId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "用户id不能为空");
        }
        //这里先插入公司信息
        Company company=new Company();
        BeanUtil.copyProperties(experienceAllDto, company);
        company.setCreateTime(new Date());
        companyMapper.insert(company);
        //这里插入工作经历信息
        Experience experience=new Experience();
        BeanUtil.copyProperties(experienceAllDto, experience);
        //将新建的公司id插入到工作经历信息
        experience.setCompanyId(company.getId());
        experience.setCreateTime(new Date());
        experienceMapper.insert(experience);
        //将信息返回
        CompanyExperienceVo companyExperienceVo=new CompanyExperienceVo();
        companyExperienceVo.setCompany(company);
        companyExperienceVo.setExperience(experience);
        return companyExperienceVo;
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
    public ExperienceAllVo get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        ExperienceAllVo experienceAllVo= experienceMapper.findById(id);
        return experienceAllVo;
    }

    /**
     * 根据条件查询list
     * @param experienceDto
     * @return
     */
    public List<ExperienceAllVo> list(ExperienceDto experienceDto){
        if(experienceDto.getUserId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403,"用户id不能为空");
        }
        Experience experience=new Experience();
        BeanUtil.copyProperties(experienceDto, experience);
        return experienceMapper.findAll(experience);
    }

}
