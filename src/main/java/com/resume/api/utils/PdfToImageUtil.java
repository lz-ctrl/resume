package com.resume.api.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lz
 */

@Component
public class PdfToImageUtil {


    /**
     * dpi越大转换后越清晰，相对转换速度越慢
     */

    private static final Integer DPI = 100;


    /**
     * 转换后的图片类型
     */

    private static final String IMG_TYPE = "png";

    private static String IMG;
    @Value("${file.img}")
    public void setImg(String img){
        IMG=img;
    }

    /**
     * PDF转图片
     * @param filePath
     * @return 图片路径list
     * @throws IOException
     */
    public static List<String> pdfToImageList(String filePath ) throws IOException {
        File file=new File(filePath);
        PDDocument pdDocument = PDDocument.load(file);
        int pageCount = pdDocument.getNumberOfPages();
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        List<String> imagePathList=new ArrayList<>();
        String fileParent = file.getParent();
        for (int pageIndex=0; pageIndex<pageCount; pageIndex++) {
            //这里给图片生成访问地址
            //String imgPath = fileParent + File.separator +".png";
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
            //这里生成图片给图片指定类型，并且命名
            String fileName=OnlyStringUtil.OnlyStringUUId()+"_img"+pageIndex+".png";
            ImageIO.write(image, IMG_TYPE, new File(IMG+fileName));
            //TODO 正式服务器需要拼接域名下载地址
            String imgPath="http://47.112.187.145:8080/img/"+fileName;
            imagePathList.add(imgPath);
        }
        pdDocument.close();
        return imagePathList;
    }

    public static void main(String[] args) throws IOException {
       // File file = new File("E:\\test2.pdf");
        List<String> stringList=pdfToImageList("E:\\HelloWorld_CN_HTML.pdf");
        System.out.println(stringList.get(0)+"==============="+stringList.size());
        //return imagePathList;
    }
}

