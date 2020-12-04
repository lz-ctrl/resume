package com.resume.api.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * @author lz
 * 根据简历的id用来生成html模板
 * Html用来转成PDF
 */
public class HtmlService {


    public String firstHtml(Integer resumeId){

        //用于存储html字符串
        StringBuilder stringHtml = new StringBuilder();

        try{
        //打开文件
            PrintStream printStream = new PrintStream(new FileOutputStream("E:\\test.html"));
            //输入HTML文件内容
            stringHtml.append("<html><head>");
            stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
            stringHtml.append("<title>测试报告文档</title>");
            stringHtml.append("</head>");
            stringHtml.append("<body>");
            stringHtml.append("<div>hello</div>");
            stringHtml.append("</body></html>");
            //将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }



        return null;
    }

}
