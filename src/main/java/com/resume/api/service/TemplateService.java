package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.resume.api.dao.TemplateMapper;
import com.resume.api.entity.Template;
import org.springframework.stereotype.Service;

/**
 * @author lz
 */

@Service
public class TemplateService {

    private final TemplateMapper templateMapper;

    public TemplateService(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    public Template findRandByName(String name){
        return templateMapper.findRandByName(name);
    }

    /**
     * 根据条数查询一条模板
     * @param name 模板名称
     * @param num 第几条
     * @return
     */
    public Template findLimitByName(String name,Integer num){
        int count=templateMapper.selectCount(new EntityWrapper<Template>().like("name",name));
        Template template=templateMapper.findLimitByName(name,num);
        if(template!=null){
            template.setCount(count);
        }else{
            template.setCount(0);
        }
        return template;
    }
}
