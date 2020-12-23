package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.EducationMapper;
import com.resume.api.dao.ExperienceMapper;
import com.resume.api.dao.ResumeMapper;
import com.resume.api.dao.SchoolMapper;
import com.resume.api.entity.Awards;
import com.resume.api.entity.Education;
import com.resume.api.entity.Experience;
import com.resume.api.entity.Interest;
import com.resume.api.entity.Resume;
import com.resume.api.entity.School;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.OnlyStringUtil;
import com.resume.api.vo.ExperienceAllVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @author lz
 * 根据简历的id用来生成html模板
 * Html用来转成PDF
 */
@Service
public class HtmlService {

    private final ResumeMapper resumeMapper;
    private final ExperienceMapper experienceMapper;
    private final SchoolMapper schoolMapper;
    private final EducationMapper educationMapper;
    private final AwardsService awardsService;
    private final InterestService interestService;

    public HtmlService(ResumeMapper resumeMapper, ExperienceMapper experienceMapper, SchoolMapper schoolMapper, EducationMapper educationMapper, AwardsService awardsService, InterestService interestService) {
        this.resumeMapper = resumeMapper;
        this.experienceMapper = experienceMapper;
        this.schoolMapper = schoolMapper;
        this.educationMapper = educationMapper;
        this.awardsService = awardsService;
        this.interestService = interestService;
    }

    /**
     *这里是HTML存放的路径
     */
    @Value("${PDF.DEST}")
    private String DEST;

    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");

    /**
     * 测试用图片base64编码
     */
    private static final String IMG = "data:image/jpg;base64,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";

    /**
     * 传入简历的id，查询出简历的所有数据
     * 遍历数据到HTML里面
     * @param resumeId
     * @param userId
     * @return
     */
    public String firstHtml(Integer resumeId,Integer userId){
        //查询出该简历内容
        Resume resume=resumeMapper.selectById(resumeId);
        if(resume==null){
            throw new ServiceException(RestCode.BAD_REQUEST_416,"简历信息不存在");
        }
        //用于存储html字符串
        StringBuffer stringHtml = new StringBuffer();
        String stringHtmlS="";
        try{
            //打开/新建HTML文件
            String HTML_URL=DEST+ OnlyStringUtil.OnlyStringDate()+"_html.html";
            PrintStream printStream = new PrintStream(HTML_URL);
            //写入HTML文件内容
            stringHtml.append("<!DOCTYPE html>");
            stringHtml.append("<html lang=\"en\"><head><meta charset=\"utf-8\" /><title>简历</title><style>");
            stringHtml.append("body{font-family:SimHei;}.fonts{font-size: 12px;}.fonth{font-size: 14px; font-weight: bold;}.fontb{font-size: 16px; font-weight: bold;}\n");
            stringHtml.append(".fontdate{font-size: 12px; margin-top: 8px; float: right;}.jl{width: 100%;margin: auto;}.jls{margin: auto;}\n");
            stringHtml.append("</style></head><body>");
            stringHtml.append("<div style=\"width: 100%; height: 165px; margin: auto;\">");
            stringHtml.append("<div style=\"width:40%; height: 120px; float: left;\">");
            stringHtml.append("<span style=\"font-size: 20px; font-weight: bold;\">"+resume.getName()+"</span><br /><br />");
            stringHtml.append("<span class=\"fonts\">"+resume.getPhone()+" 丨"+resume.getEmailWx()+" <br /><br />");
            if(resume.getExpect()!=null){
                stringHtml.append("求职意向: "+resume.getExpect());
            }
            if(resume.getSalary()!=null){
                stringHtml.append( "丨期望薪资: "+resume.getSalary());
            }
            stringHtml.append("求职意向: "+resume.getExpect()+" 丨 期望薪资: "+resume.getSalary());
            stringHtml.append("</span></div><br />");
            stringHtml.append("<div style=\"height: 180px; float: right;\"><img style=\"width: 100px; height: 100px;\" src=\""+resume.getHeadImg()+"\" /></div></div>");
            //这里是所在学校的DIV模块
            List<School> schoolList=schoolMapper.findByResumeIdLevel(resumeId);
            if(schoolList.size()>0){
                stringHtml.append("<div class=\"jl\">");
                stringHtml.append("<span class=\"fontb\">教育经历</span><hr />");
                schoolList.forEach(school -> {
                    String startTime=sdf.format(school.getStartTime());
                    String endTime=sdf.format(school.getEndTime());
                    stringHtml.append("<div class=\"jls\"><span class=\"fonth\">"+school.getName()+"</span><span class=\"fonts\"> - "+school.getMajor()+" "+school.getStudyLevelName()+"</span><div class=\"fontdate\" >"+startTime+" - "+endTime+"</div></div><br />");
                });
                stringHtml.append("</div>");
            }
            //这里开始是工作经历DIV模块
            List<ExperienceAllVo> allVos=experienceMapper.findAll(new Experience().setResumeId(resumeId).setUserId(userId));
            if(allVos.size()>0){
                stringHtml.append("<div class=\"jl\">");
                stringHtml.append("<span class=\"fontb\">工作经历</span><hr />");
                allVos.forEach(experienceAllVo -> {
                    stringHtml.append("<div class=\"jls\"><span class=\"fonth\">"+experienceAllVo.getName()+"</span><span class=\"fonts\"> - "+experienceAllVo.getPost()+"</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
                    if(experienceAllVo.getContent()!=null&&!"".equals(experienceAllVo.getContent())) {
                        stringHtml.append("<span class=\"fonth\">内容</span>");
                        stringHtml.append("<span style=\" font-size:9pt\">");
                        stringHtml.append(experienceAllVo.getContent());
                        stringHtml.append("</span>");
                    }
                    if(experienceAllVo.getAchievement()!=null&&!"".equals(experienceAllVo.getAchievement())) {
                        stringHtml.append("<span class=\"fonth\">业绩</span>");
                        stringHtml.append("<span style=\" font-size:9pt\">");
                        stringHtml.append(experienceAllVo.getAchievement());
                        stringHtml.append("</span>");
                    }
                });
                stringHtml.append("</div>");
            }
            //这里开始是在校经历DIV模块
            List<Education> educationList=educationMapper.findAll(new Education().setResumeId(resumeId).setUserId(userId));
            if(educationList.size()>0){
                stringHtml.append("<div class=\"jl\"><br />");
                stringHtml.append("<span class=\"fontb\">在校/其它经历</span><hr />");
                educationList.forEach(education -> {
                    String startTime=sdf.format(education.getStartTime());
                    String endTime=sdf.format(education.getEndTime());
                    stringHtml.append("<div class=\"jls\"><span class=\"fonth\">"+education.getActivityName()+"</span><span class=\"fonts\"> - "+education.getActivityRole()+"</span><div class=\"fontdate\" >"+startTime+" - "+endTime+"</div></div><br />\n");
                    stringHtml.append("<span style=\" font-size:9pt\">");
                    stringHtml.append(education.getContent());
                    stringHtml.append("</span>");
                });
                stringHtml.append("</div>");
            }
            //证书获奖的DIV块
            List<Awards> awardsList=awardsService.list(resumeId);
            if(awardsList.size()>0){
                stringHtml.append("<div class=\"jl\"><br />");
                stringHtml.append("<span class=\"fontb\">证书/获奖</span><hr />");
                awardsList.forEach(awards -> {
                    stringHtml.append("<div class=\"jls\"><span class=\"fonts\">"+awards.getContent()+"</span></div><br />\n");
                });
                stringHtml.append("</div>");
            }
            //兴趣爱好的DIV块
            List<Interest> interestList=interestService.list(resumeId);
            if(interestList.size()>0){
                stringHtml.append("<div class=\"jl\"><br />");
                stringHtml.append("<span class=\"fontb\">个人兴趣爱好</span><hr />");
                interestList.forEach(interest -> {
                    stringHtml.append("<div class=\"jls\"><span class=\"fonts\">"+interest.getContent()+"</span></div><br />");
                });
                stringHtml.append("</div>");
            }
            //HTML结尾
            stringHtml.append("</body></html>");
            //将HTML文件内容写入文件中
            stringHtmlS=stringHtml.toString();
            stringHtmlS = stringHtmlS.replace("<br>","<br />");
            printStream.println(stringHtmlS);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        //将HTML的内容返回过去
        return stringHtmlS;
    }

}
