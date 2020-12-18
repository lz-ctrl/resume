package com.resume.api.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.resume.api.codec.RestCode;
import com.resume.api.dao.AwardsMapper;
import com.resume.api.dao.EducationMapper;
import com.resume.api.dao.ExperienceMapper;
import com.resume.api.dao.InterestMapper;
import com.resume.api.dao.ResumeMapper;
import com.resume.api.dao.SchoolMapper;
import com.resume.api.dto.ResumeDto;
import com.resume.api.entity.Awards;
import com.resume.api.entity.Education;
import com.resume.api.entity.Experience;
import com.resume.api.entity.Interest;
import com.resume.api.entity.Resume;
import com.resume.api.entity.School;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.BeanUtil;
import com.resume.api.utils.FileUtil;
import com.resume.api.utils.ImgBase64Util;
import com.resume.api.vo.IfResumeVo;
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

    private final ResumeMapper resumeMapper;
    private final EducationMapper educationMapper;
    private final AwardsMapper awardsMapper;
    private final InterestMapper interestMapper;
    private final SchoolMapper schoolMapper;
    private final ExperienceMapper experienceMapper;

    public ResumeService(ResumeMapper resumeMapper, EducationMapper educationMapper, AwardsMapper awardsMapper, InterestMapper interestMapper, SchoolMapper schoolMapper, ExperienceMapper experienceMapper) {
        this.resumeMapper = resumeMapper;
        this.educationMapper = educationMapper;
        this.awardsMapper = awardsMapper;
        this.interestMapper = interestMapper;
        this.schoolMapper = schoolMapper;
        this.experienceMapper = experienceMapper;
    }

    public String getData(){
        return null;
    }

    private final String RESUME_NAME="null_null_个人简历";

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
        resume.setResumeName(resumeDto.getName()+"_"+resumeDto.getExpect()+"_个人简历");
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
        //如果没有修改简历名称,则自动给他生成简历名称
        String resumeName=resumeMapper.selectById(resumeDto.getId()).getResumeName();
        if(resumeDto.getResumeName()==null&&resumeName.equals(RESUME_NAME)){
            resume.setResumeName(resumeDto.getName()+"_"+resumeDto.getExpect()+"_个人简历");
        }
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

    /**
     * 这里判断简历是否有未填写的信息
     * @return
     */
    public IfResumeVo judgeResume(Integer resumeId){
        IfResumeVo ifResumeVo=new IfResumeVo();
        Resume resume=resumeMapper.selectById(resumeId);
        if(resume==null){
            throw new ServiceException(RestCode.BAD_REQUEST_416,"简历信息不存在");
        }
        ifResumeVo.setIfHeadImg(resume.getHeadImg()==null?0:1);
        ifResumeVo.setIfExpect(resume.getExpect()==null?0:1);
        ifResumeVo.setIfUser(resume.getName()==null?0:1);
        //统计出该简历的其他信息
        Integer educationCount=educationMapper.selectCount(new EntityWrapper<Education>().eq("resume_id",resumeId));
        Integer awardsCount=awardsMapper.selectCount(new EntityWrapper<Awards>().eq("resume_id",resumeId));
        Integer interestCount=interestMapper.selectCount(new EntityWrapper<Interest>().eq("resume_id", resumeId));
        Integer schoolCount=schoolMapper.selectCount(new EntityWrapper<School>().eq("resume_id", resumeId));
        Integer experienceCount=experienceMapper.selectCount(new EntityWrapper<Experience>().eq("resume_id", resumeId));
        ifResumeVo.setIfEducation(educationCount<=0?0:1);
        ifResumeVo.setIfAwards(awardsCount<=0?0:1);
        ifResumeVo.setIfInterest(interestCount<=0?0:1);
        ifResumeVo.setIfSchool(schoolCount<=0?0:1);
        ifResumeVo.setIfExperience(experienceCount<=0?0:1);
        return ifResumeVo;
    }
}
