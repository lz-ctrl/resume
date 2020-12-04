package com.resume.api.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author lz
 */

public class JavaToPdfHtmlUtli {


    private static final String DEST = "E:\\HelloWorld_CN_HTML.pdf";
    private static final String HTML = "E:\\1204113159702.html";
    private static final String FONT = "E:\\simhei.ttf";
    private static final String FONT2 = "E:\\Microsoft YaHei UI Bold.ttf";


    public static void main(String[] args) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        fontImp.register(FONT2);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                                 new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();
    }
}
