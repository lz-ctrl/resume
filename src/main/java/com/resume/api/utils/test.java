package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

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

    public static void main(String[] args) {
        //用于存储html字符串
        StringBuilder stringHtml = new StringBuilder();

        try{
            //打开文件
            PrintStream printStream = new PrintStream(HTML);
            //输入HTML文件内容
            stringHtml.append("<html><head>");
            stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />");
            stringHtml.append("<title>测试报告文档</title>");
            stringHtml.append("</head>");
            stringHtml.append("<body>");
            for(int i=0;i<3;i++){
                stringHtml.append("<div>hello</div>");
            }
            stringHtml.append("</body></html>");
            //将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
            /**
             * 这里把HTML转成PDF
             */
            // step 1
            Document document = new Document();
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
            // step 3
            document.open();
            // step 4
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(FONT);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                                     new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
            // step 5
            document.close();
        }catch(FileNotFoundException | DocumentException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
