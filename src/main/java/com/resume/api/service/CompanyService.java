package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.CompanyMapper;
import com.resume.api.dto.CompanyDto;
import com.resume.api.entity.Company;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lz
 */
@Service
public class CompanyService {

    private final CompanyMapper companyMapper;

    public CompanyService(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    /**
     * 新增学校教育信息
     * @param companyDto
     * @return
     */
    public Company create(CompanyDto companyDto){
        if(companyDto.getResumeId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403, "简历id不能为空");
        }
        Company company=new Company();
        BeanUtil.copyProperties(companyDto, company);
        company.setCreateTime(new Date());
        companyMapper.insert(company);
        return company;
    }


    /**
     * 修改学校教育信息
     * @param companyDto
     * @return
     */
    public Company update(CompanyDto companyDto){
        if(companyDto.getId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Company company=new Company();
        BeanUtil.copyProperties(companyDto, company);
        companyMapper.updateById(company);
        return company;
    }

    /**
     * 根据id查询学校教育信息
     * @param id
     * @return
     */
    public Company get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Company company= companyMapper.selectById(id);
        return company;
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
        return companyMapper.deleteById(id);
    }
}
