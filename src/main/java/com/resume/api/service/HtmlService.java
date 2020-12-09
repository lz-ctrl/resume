package com.resume.api.service;

import com.resume.api.codec.RestCode;
import com.resume.api.dao.ExperienceMapper;
import com.resume.api.dao.ResumeMapper;
import com.resume.api.dao.UserMapper;
import com.resume.api.entity.Experience;
import com.resume.api.entity.Resume;
import com.resume.api.entity.School;
import com.resume.api.entity.User;
import com.resume.api.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;


/**
 * @author lz
 * 根据简历的id用来生成html模板
 * Html用来转成PDF
 */
@Service
public class HtmlService {

    private final ResumeMapper resumeMapper;
    private final UserMapper userMapper;
    private final ExperienceMapper experienceMapper;

    public HtmlService(ResumeMapper resumeMapper, UserMapper userMapper, ExperienceMapper experienceMapper) {
        this.resumeMapper = resumeMapper;
        this.userMapper = userMapper;
        this.experienceMapper = experienceMapper;
    }

    /**
     *这里是HTML存放的路径
     */
    private static final String HTML_URL = "E:\\test.html";

    /**
     * 测试用图片base64编码
     */
    private static final String IMG = "data:image/jpg;base64,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";

    /**
     * 传入简历的id，查询出简历的所有数据
     * 遍历数据到HTML里面
     * @param resumeId
     * @return
     */
    public String firstHtml(Integer resumeId){
        //查询出该简历内容
        Resume resume=resumeMapper.selectById(resumeId);
        //查询出用户信息
        User user=userMapper.selectById(resume.getUserId());
        if(user==null){
            throw new ServiceException(RestCode.TOKEN_ERROR_503);
        }

        Experience experience= experienceMapper.selectById(1);
        //用于存储html字符串
        StringBuffer stringHtml = new StringBuffer();
        try{
            //打开/新建HTML文件
            PrintStream printStream = new PrintStream(HTML_URL);
            //写入HTML文件内容
            stringHtml.append("<!DOCTYPE html>");
            stringHtml.append("<html lang=\"en\"><head><meta charset=\"utf-8\" /><title>简历</title><style>");
            stringHtml.append("body{font-family:SimHei;}.fonts{font-size: 12px;}.fonth{font-size: 14px; font-weight: bold;}.fontb{font-size: 16px; font-weight: bold;}\n");
            stringHtml.append(".fontdate{font-size: 12px; margin-top: 8px; float: right;}.jl{width: 100%;margin: auto;}.jls{margin: auto;}\n");
            stringHtml.append("</style></head><body>");
            stringHtml.append("<div style=\"width: 100%; height: 165px; margin: auto;\">");
            stringHtml.append("<div style=\"width:40%; height: 120px; float: left;\">");
            stringHtml.append("<span style=\"font-size: 20px; font-weight: bold;\">言职青年</span><br /><br />");
            stringHtml.append("<span class=\"fonts\">"+user.getPhone()+" 丨"+user.getEmail()+" <br /><br />求职意向: UI设计师 丨期望薪资: 6-8k</span></div><br />");
            stringHtml.append("<div style=\"height: 180px; float: right;\"><img style=\"width: 100px; height: 100px;\" src=\""+IMG+"\" /></div></div>");
            stringHtml.append("<div class=\"jl\">");
            stringHtml.append("<span class=\"fontb\">教育经历</span><hr />");
            //这里是所在学校的DIV模块
            for(int i=0;i<1;i++){
                stringHtml.append("<div class=\"jls\"><span class=\"fonth\">杜伦大学</span><span class=\"fonts\"> - 金融学 本科</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />");
            }
            stringHtml.append("</div>");
            stringHtml.append("<div class=\"jl\">");
            stringHtml.append("<span class=\"fontb\">工作经历</span><hr />");

            //这里开始是工作经历DIV模块
            stringHtml.append("<div class=\"jls\"><span class=\"fonth\">北京咨询有限公司</span><span class=\"fonts\"> - 兼职项目助理</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
            for(int i=0;i<1;i++){
                stringHtml.append("<span class=\"fonth\">内容</span>");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append(experience.getContent());
                stringHtml.append("</ul>");
                stringHtml.append("<span class=\"fonth\">业绩</span>");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append(experience.getAchievement());
                stringHtml.append("</ul>");
            }
            stringHtml.append("</div>");

            //这里开始是在校经历DIV模块
            stringHtml.append("<div class=\"jl\"><br />");
            stringHtml.append("<span class=\"fontb\">在校/其它经历</span><hr />");
            for(int i=0;i<1;i++){
                stringHtml.append("<div class=\"jls\"><span class=\"fonth\">校学生会</span><span class=\"fonts\"> - 学生会委员</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append("<li>协调校团委各部门的工作，协同校团委文体部、组织部，先后成功组织“闪青”、“彩跑”等大型学院活动，活动平 均参与人数500+</li>");
                stringHtml.append("<li>创建并运营团委微信公众平台，审核编辑推送内容，报道团委活动，订阅号平均阅读量1000+</li>");
                stringHtml.append("</ul>");
            }
            for(int i=0;i<1;i++){
                stringHtml.append("<br /><div class=\"jls\"><span class=\"fonth\">组织培训竞赛</span><span class=\"fonts\"> - 活动负责人</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append("<li>开展赛前宣传，吸引了400余名同学参加初赛。组织校内选拔和培训，邀请了来自包括清华大学等北京高校在内的13 名老师开展培训，并选拔出20名同学代表学校参赛</li>");
                stringHtml.append("<li>负责培训内容细化，收集分析历年比赛试卷，将比赛内容细分为5个方面，并辅导两支参赛队伍</li>");
                stringHtml.append("</ul>");
            }
            stringHtml.append("</div>");
            //证书获奖的DIV块
            if(resume.getAwards()!=null&&!"".equals(resume.getAwards())){
                stringHtml.append("<div class=\"jl\"><br />");
                stringHtml.append("<span class=\"fontb\">证书/获奖</span><hr />");
                stringHtml.append("<div class=\"jls\"><span class=\"fonts\">");
                stringHtml.append(resume.getAwards());
                stringHtml.append("</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />");
                stringHtml.append("</div>");
            }
            //兴趣爱好的DIV块
            if(resume.getInterest()!=null&&!"".equals(resume.getInterest())){
            stringHtml.append("<div class=\"jl\"><br />");
            stringHtml.append("<span class=\"fontb\">个人兴趣爱好</span><hr />");
            stringHtml.append("<div class=\"jls\"><span class=\"fonts\">");
            stringHtml.append(resume.getInterest());
            stringHtml.append("</span></div><br />");
            stringHtml.append("</div>");
            }
            //HTML结尾
            stringHtml.append("</body></html>");
            //将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        //将HTML的内容返回过去
        return stringHtml.toString();
    }

}
