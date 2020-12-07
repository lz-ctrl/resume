package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * @author lz
 * 测试文件，用来调试
 */
public class test {

    private static final String DEST = "E:\\HelloWorld_CN_HTML.pdf";
    private static final String HTML = "E:\\test.html";
    private static final String FONT = "E:\\simhei.ttf";
    private static final String IMG  = "data:image/jpg;base64,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";


    public static void main(String[] args) {
        //用于存储html字符串
        StringBuilder stringHtml = new StringBuilder();

        try{
            //打开/新建HTML文件
            PrintStream printStream = new PrintStream(HTML);
            //写入HTML文件内容
            stringHtml.append("<!DOCTYPE html>");
            stringHtml.append("<html lang=\"en\"><head><meta charset=\"utf-8\" /><title>简历</title><style>");
            stringHtml.append("body{font-family:SimHei;}.fonts{font-size: 12px;}.fonth{font-size: 14px; font-weight: bold;}.fontb{font-size: 16px; font-weight: bold;}\n");
            stringHtml.append(".fontdate{font-size: 12px; margin-top: 8px; float: right;}.jl{width: 100%;margin: auto;}.jls{margin: auto;}\n");
            stringHtml.append("</style></head><body>");
            stringHtml.append("<div style=\"width: 100%; height: 165px; margin: auto;\">");
            stringHtml.append("<div style=\"width:40%; height: 120px; float: left;\">");
            stringHtml.append("<span style=\"font-size: 20px; font-weight: bold;\">言职青年</span><br /><br />");
            stringHtml.append("<span class=\"fonts\">17765975427 丨wangshan@163.com <br /><br />求职意向: UI设计师 丨期望薪资: 6-8k</span></div><br />");
            stringHtml.append("<div style=\"height: 180px; float: right;\"><img style=\"width: 100px; height: 100px;\" src=\""+IMG+"\" /></div></div>");
            stringHtml.append("<div class=\"jl\">");
            for(int i=0;i<1;i++){
                stringHtml.append("<span class=\"fontb\">教育经历</span><hr />");
                stringHtml.append("<div class=\"jls\"><span class=\"fonth\">杜伦大学</span><span class=\"fonts\"> - 金融学 本科</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />");
            }
            stringHtml.append("</div>");
            stringHtml.append("<div class=\"jl\">");
            stringHtml.append("<span class=\"fontb\">工作经历</span><hr />");
            stringHtml.append("<div class=\"jls\"><span class=\"fonth\">北京咨询有限公司</span><span class=\"fonts\"> - 兼职项目助理</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
            for(int i=0;i<1;i++){
                stringHtml.append("<span class=\"fonth\">内容</span>");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append("<li>协助咨询顾问建立客户财务模型，预测其5年内逐年资产规模并划分业务权重，例如对公业务vs零售业务</li>");
                stringHtml.append("<li>对短视频领域进行深入行业研究，以查询公开数据、公司财报、用户寻访等方式，调研短视频市场和头部App运营情况，为商业银行创新业务板块寻找潜在突破机会，重点剖析中美共4家公司，整理为行业研究文档，并完成超过30页的幻灯片制作，被采纳成为最终递呈材料的组成部分</li>");
                stringHtml.append("</ul>");
                stringHtml.append("<span class=\"fonth\">业绩</span>");
                stringHtml.append("<ul style=\" font-size:9pt\">");
                stringHtml.append("<li>目标kpi绩效超额20%完成</li>");
                stringHtml.append("<li>通过咨询工作给公司带来非常大的收益</li>");
                stringHtml.append("</ul>");
            }
            stringHtml.append("</div>");
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
            stringHtml.append("<div class=\"jl\"><br />");
            stringHtml.append("<span class=\"fontb\">证书/获奖</span><hr />");
            stringHtml.append("<div class=\"jls\"><span class=\"fonts\">CPA、CFA备考金融风险管理师（FRM）、英语（CET-6）</span><div class=\"fontdate\" >2018.10 - 2019.06</div></div><br />\n");
            stringHtml.append("</div>");
            stringHtml.append("<div class=\"jl\"><br />");
            stringHtml.append("<span class=\"fontb\">个人兴趣爱好</span><hr />");
            stringHtml.append("<div class=\"jls\"><span class=\"fonts\">热爱写作、热爱篮球(校队长)</span></div><br />");
            stringHtml.append("</div>");
            stringHtml.append("</body></html>");
            //将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
            /**
             * 这里把HTML转成PDF
             */
            ITextRenderer render = new ITextRenderer();
            ITextFontResolver fontResolver = render.getFontResolver();
            fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 解析html生成pdf
            render.setDocumentFromString(stringHtml.toString());
            //解决图片相对路径的问题
            render.getSharedContext().setBaseURL(IMG);
            render.layout();
            render.createPDF(new FileOutputStream(DEST));

        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }
}
