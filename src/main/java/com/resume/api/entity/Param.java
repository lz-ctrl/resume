package com.resume.api.entity;

import lombok.Data;

/**
 * @author lz
 */
@Data
public class Param {
    //资源文件路径
    private String resourceDir;
    //字体文件名称
    private String fontName;
    //FTL模板文件名称
    private String ftlName;
    //FTL模板文件字符集
    private String charset;
    //输出PDF文件完整路径
    private String outputDir;
}
