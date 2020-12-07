package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author lz
 */

public class JavaToPdfHtmlUtli {


    private static final String DEST = "E:\\HelloWorld_CN_HTML.pdf";
    private static final String HTML = "E:\\test.html";
    private static final String FONT = "E:\\simhei.ttf";

    private static final String IMG = "E:\\timg.jpg";


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
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                                 new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
        return DEST;
    }

    /**
     * HTML转换为PDF 高级CSS 图片
     * @param stringHtml
     * @return
     * @throws IOException
     * @throws com.lowagie.text.DocumentException
     */
    public static String CreatePDFRenderer(String stringHtml) throws IOException, com.lowagie.text.DocumentException {
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        fontResolver.addFont(FONT, com.itextpdf.text.pdf.BaseFont.IDENTITY_H, com.itextpdf.text.pdf.BaseFont.NOT_EMBEDDED);
        // 解析html生成pdf
        render.setDocumentFromString(stringHtml.toString());
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL(IMG);
        render.layout();
        render.createPDF(new FileOutputStream(DEST));

        return DEST;
    }

    public static void main(String[] args) {


    }
}
