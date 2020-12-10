package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author lz
 */
@Component
public class JavaToPdfHtmlUtil {

    private static String DEST;
    private static String HTML;
    private static String FONT;

    @Value("${PDF.DEST}")
    public void setDEST(String dest){
        DEST=dest;
    }
    @Value("${PDF.HTML}")
    public void setHTML(String html){
        HTML=html;
    }
    @Value("${PDF.FONT}")
    public void setFONT(String font){
        FONT=font;
    }

    /**
     * HTML转换为PDF 无高级CSS样式以及图片
     * @param htmlUrl
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String CreatePDFXMLWorker(String htmlUrl) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        //生成html文件名称
        String htmlPath=HTML+OnlyStringUtil.OnlyStringUUId()+".html";
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                                 new FileInputStream(htmlPath), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
        return DEST;
    }

    /**
     * HTML转换为PDF 高级CSS 图片
     * @param stringHtml
     * @param key 0返回访问路径 1返回绝对路径
     * @return
     * @throws IOException
     * @throws com.lowagie.text.DocumentException
     */
    public static String CreatePDFRenderer(String stringHtml,Integer key) throws IOException, com.lowagie.text.DocumentException {
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, com.itextpdf.text.pdf.BaseFont.IDENTITY_H, com.itextpdf.text.pdf.BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(stringHtml);
        render.layout();
        String fileName=OnlyStringUtil.OnlyStringUUId()+".pdf";
        //这里生成PDF文件
        String path=DEST+fileName;
        render.createPDF(new FileOutputStream(path));
        //TODO 正式服务器需要拼接域名下载地址
        String resultPath="http://47.112.187.145:8080/office/"+fileName;
        //这里的key用来判断是否返回访问路径还是绝对路径
        if(key==0){
            return resultPath;
        }else{
            return path;
        }
    }


    public static void main(String[] args) {


    }
}
