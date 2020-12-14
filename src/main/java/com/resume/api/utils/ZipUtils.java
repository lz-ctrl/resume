package com.resume.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lz
 */
@Component
public class ZipUtils {


    private static String imgUrl;

    @Value("${file.img}")
    public void setImgUrl(String img){
        imgUrl=img;
    }
    /**
     * 批量打包
     * 这里返回的是存储的绝对路径
     * @param stringList 文件地址的list数组
     * @return zip文件保存绝对路径
     */
    public static String createZipAndReturnPath(List<String> stringList) {
        //生成jsonArray列表
        try {
            //生成打包下载后的zip文件:Papers.zip
            String papersZipName = System.currentTimeMillis()+"Papers.zip";
            //zip文件保存路径
            String zipPath = imgUrl + papersZipName;
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
            //遍历jsonArray列表获取所有JSONObject对象
            for (int i = 0; i < stringList.size(); i++) {
                //获得文件相对路径
                String relativePath = stringList.get(i);
				//获得文件名
	            String fileName = relativePath.substring(relativePath.lastIndexOf("/")+1);
         	    //TODO 获得下载文件完整路径 需要用时拼接域名或者IP地址
                String downloadPath = "imgUrl" + relativePath;
                //以论文标题为每个文件命名
                FileInputStream fis = new FileInputStream(relativePath);
                out.putNextEntry(new ZipEntry(fileName));
                //写入压缩包
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
            out.flush();
            return zipPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   

}
