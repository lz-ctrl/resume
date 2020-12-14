package com.resume.api.utils;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lz
 */
@Component
public class SpireUtil {

    private static String DEST;
    @Value("${PDF.DEST}")
    public void setDEST(String dest){
        DEST=dest;
    }
    /**
     * PDF转word格式
     * @param fileString
     * @return
     */
    public static String PDFToWord(String fileString,Integer key){
        //这里转成WORD
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(fileString);
        String fileName=OnlyStringUtil.OnlyStringUUId()+".docx";
        String path=DEST+fileName;
        pdf.saveToFile(path, FileFormat.DOCX);
        //TODO 正式服务器需要拼接域名下载地址
        String resultPath="http://47.112.187.145:8080/office/"+fileName;
        if(key==0){
            return resultPath;
        }else{
            return path;
        }
    }
}
