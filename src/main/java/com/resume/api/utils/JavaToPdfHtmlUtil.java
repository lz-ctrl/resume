package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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


    @Autowired
    private static Configuration freemarkerCfg;


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
        String htmlPath=DEST+OnlyStringUtil.OnlyStringUUId()+".html";
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

    /**
     * freemarker渲染html
     * 使用HTML模板进行导出HTML操作
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取HTML模板,并设置编码方式
            freemarkerCfg =new Configuration();
            // 设置HTML模板路径
            TemplateLoader templateLoader=new FileTemplateLoader(new File(HTML));
            freemarkerCfg.setTemplateLoader(templateLoader);
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            // 将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            String path=HTML+htmlTmp;
            out.flush();
            System.out.println(HTML+htmlTmp);
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, com.lowagie.text.DocumentException {
        Map<String,Object> data = new HashMap();
        data.put("phone","123123213213213");
        data.put("emailWx","OAWFEHIUEWAFHIAEWOUFHEAO");
        data.put("expect","帅的丰富的");
        data.put("salary","100000");
        data.put("content","<ol><li>rose</li><li>lisa</li><li>jisoo</li><li>jennie</li><li>blackpink</li></ol>");
        String content = JavaToPdfHtmlUtil.freeMarkerRender(data,"demo.html");
        JavaToPdfHtmlUtil.CreatePDFRenderer(content,1);

    }
}
