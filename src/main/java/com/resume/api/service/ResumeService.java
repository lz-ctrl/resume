package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.ResumeMapper;
import com.resume.api.dto.ResumeDto;
import com.resume.api.entity.Resume;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import com.resume.api.utils.FileUtil;
import com.resume.api.utils.ImgBase64Util;
import com.resume.api.vo.PageVo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 根据id查询简历
     * @param id
     * @return
     */
    public Resume get(Integer id){
        if(id==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403);
        }
        Resume resume= resumeMapper.selectById(id);
        return resume;
    }

    /**
     * 分页查询简历
     * @param resumeDto
     * @return
     */
    public PageVo page(ResumeDto resumeDto){
        if(resumeDto.getUserId()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403,"用戶id不能为空");
        }
        if(resumeDto.getPage()==null||resumeDto.getSize()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_414);
        }
        Page<Resume> page=new Page<>(resumeDto.getPage(),resumeDto.getSize());
        //设置条件
        EntityWrapper<Resume> wrapper =new EntityWrapper<>();
        //设置userId条件
        wrapper.eq("user_id", resumeDto.getUserId());
        Integer count = resumeMapper.selectCount(wrapper);
        PageVo pageVo=new PageVo();
        pageVo.setList(resumeMapper.selectPage(page,wrapper));
        pageVo.setCount(count);
        return pageVo;
    }

    /**
     * 头像
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        // 图片路径
        StringBuilder stringBuilder = new StringBuilder();
        // 文件不大于5M
        if(!FileUtil.checkFileSize(file.getSize(), 5, "M")){
            throw new ServiceException(RestCode.BAD_REQUEST_406,"图片大小不得超过5MB");
        }
        try {
            String path = FileUtil.upload(file, file.getOriginalFilename());
            Thumbnails.of(path)
                      .scale(0.1f)
                      .outputQuality(0.5f)
                      .toFile(path);
            //转出base64编码
            stringBuilder.append("data:image/jpg;base64,"+ImgBase64Util.getImgStr(path));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(RestCode.BAD_REQUEST_406);
        }
        return stringBuilder.toString();
    }
}
