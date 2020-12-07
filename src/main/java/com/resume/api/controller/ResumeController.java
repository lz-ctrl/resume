package com.resume.api.controller;

import com.lowagie.text.DocumentException;
import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.ResumeDto;
import com.resume.api.exception.ServiceException;
import com.resume.api.service.HtmlService;
import com.resume.api.service.ResumeService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.utils.JavaToPdfHtmlUtli;
import com.resume.api.vo.ResumeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lz
 */
@Api(tags = "简历接口")
@RestController
@RequestMapping("resume")
public class ResumeController {
    private final ResumeService resumeService;
    private final HtmlService htmlService;

    public ResumeController(ResumeService resumeService, HtmlService htmlService) {
        this.resumeService = resumeService;
        this.htmlService = htmlService;
    }

    @ApiOperation(value = "新增简历",notes = "新增简历")
    @PostMapping()
    public RestApiResult<ResumeVo> create(@RequestBody @Validated ResumeDto resumeDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(resumeService.create(resumeDto), ResumeVo.class));
    }

    @ApiOperation(value = "修改简历",notes = "修改简历")
    @PutMapping()
    public RestApiResult<ResumeVo> update(@RequestBody @Validated ResumeDto resumeDto){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(resumeService.update(resumeDto), ResumeVo.class));
    }

    @ApiOperation(value = "删除简历",notes = "删除简历")
    @DeleteMapping("{id}")
    public RestApiResult delete(@PathVariable Integer id){
        resumeService.delete(id);
        return new RestApiResult<>(RestCode.SUCCESS);
    }


    @ApiOperation(value = "导出简历",notes = "导出简历")
    @RequestMapping("export")
    public void exportResume(Integer id, String key, HttpServletResponse response) throws IOException, DocumentException {
        String path="";
        switch(key){
            case "PDF":
                //表示导出PDF
                String stringHtml = htmlService.firstHtml(id);
                path=JavaToPdfHtmlUtli.CreatePDFRenderer(stringHtml);
                break;
            case "PNG":
                //表示导出图片
                break;
            case "DOC":
                //表示导出word文档
                break;
            default:
                throw new ServiceException(RestCode.BAD_REQUEST_408);
        }
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
