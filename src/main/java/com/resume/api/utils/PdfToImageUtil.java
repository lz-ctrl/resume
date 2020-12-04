/*
package com.resume.api.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author lz
 *//*

public class PdfToImageUtil {

    */
/**
     * dpi越大转换后越清晰，相对转换速度越慢
     *//*

    private static final Integer DPI = 100;

    */
/**
     * 转换后的图片类型
     *//*

    private static final String IMG_TYPE = "png";

    */
/**
     * PDF转图片
     *
     * @param fileContent PDF文件的二进制流
     * @return 图片文件的二进制流
     *//*

    public static List<byte[]> pdfToImage() throws IOException {
        List<byte[]> result = new ArrayList<>();
        File file = new File("E:\\test.pdf");
        try (PDDocument document = PDDocument.load(file)) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); ++i) {
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i, DPI);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, IMG_TYPE, new File("E:\\pdf_image.png"));
                result.add(out.toByteArray());
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        pdfToImage();
    }
}
*/
