package com.resume.api.controller;

import com.lowagie.text.DocumentException;
import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.dto.ExportDto;
import com.resume.api.dto.ResumeDto;
import com.resume.api.exception.ServiceException;
import com.resume.api.service.HtmlService;
import com.resume.api.service.ResumeService;
import com.resume.api.utils.BeanMapper;
import com.resume.api.utils.EmailUtil;
import com.resume.api.utils.JavaToPdfHtmlUtil;
import com.resume.api.utils.PdfToImageUtil;
import com.resume.api.utils.SpireUtil;
import com.resume.api.utils.ZipUtils;
import com.resume.api.vo.ExportVo;
import com.resume.api.vo.IfResumeVo;
import com.resume.api.vo.PageVo;
import com.resume.api.vo.ResumeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "根据id查询单个简历",notes = "根据id查询单个简历")
    @GetMapping("{id}")
    public RestApiResult<ResumeVo> get(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS, BeanMapper.map(resumeService.get(id), ResumeVo.class));
    }

    @ApiOperation(value = "根据userId查询简历列表",notes = "根据userId查询简历列表")
    @PostMapping("page")
    public RestApiResult<PageVo> page(@RequestBody @Validated ResumeDto resumeDto){
        return new RestApiResult<>(RestCode.SUCCESS, resumeService.page(resumeDto));
    }

    @ApiOperation(value = "头像上传",notes = "头像上传")
    @PostMapping("/img")
    public RestApiResult<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return new RestApiResult<>(RestCode.SUCCESS, resumeService.uploadImage(file));
    }

    @ApiOperation(value = "导出简历",notes = "导出简历")
    @PostMapping("export")
    public RestApiResult exportResume(@RequestBody ExportDto exportDto) throws Exception {
        Integer userId=exportDto.getUserId();
        Integer resumeId=exportDto.getResumeId();
        String key=exportDto.getKey();
        System.out.println(key);
        if(key==null){
            throw new ServiceException(RestCode.BAD_REQUEST_408,"key值为空");
        }
        if(userId==null){
            throw new ServiceException(RestCode.BAD_REQUEST_403,"用户id不能为空");
        }
        if(exportDto.getIfEmail()!=null&&exportDto.getIfEmail()==1&&exportDto.getEmail()==null){
            throw new ServiceException(RestCode.BAD_REQUEST_408,"请填入邮箱号");
        }
        //用来存储路径的
        String path="";
        //存储HTML信息
        String stringHtml="";
        //最终返回Vo
        ExportVo exportVo=new ExportVo();
        switch(key){
            case "PDF":
                //表示导出PDF
                stringHtml = htmlService.firstHtml(resumeId,userId);
                if(exportDto.getIfEmail()!=null&&exportDto.getIfEmail()==1){
                    //为1表示为发送邮件 调用邮件发送接口
                    path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,1);
                    EmailUtil.sendMail(exportDto.getEmail(), "言职青年", "您的PDF文件已经生成", path);
                }else{
                    path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,0);
                    exportVo.setPath(path);
                }
                break;
            case "PNG":
                //表示导出图片
                stringHtml = htmlService.firstHtml(resumeId,userId);
                path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,1);
                if(exportDto.getIfEmail()!=null&&exportDto.getIfEmail()==1){
                    List<String> list= PdfToImageUtil.pdfToImageList(path,1);
                    //这里导出图片因为可能有多张图片,所以必须先压缩再发送邮件
                    path=ZipUtils.createZipAndReturnPath(list);
                    EmailUtil.sendMail(exportDto.getEmail(), "言职青年", "您的PDF文件已经生成", path);
                }else{
                    List<String> list= PdfToImageUtil.pdfToImageList(path,0);
                    exportVo.setImgPath(list);
                }
                break;
            case "DOC":
                //表示导出word文档
                stringHtml = htmlService.firstHtml(resumeId,userId);
                path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,1);
                if(exportDto.getIfEmail()!=null&&exportDto.getIfEmail()==1){
                    path= SpireUtil.PDFToWord(path,1);
                    EmailUtil.sendMail(exportDto.getEmail(), "言职青年", "您的PDF文件已经生成", path);
                }else {
                    path= SpireUtil.PDFToWord(path,0);
                    exportVo.setPath(path);
                }
                break;
            default:
                throw new ServiceException(RestCode.BAD_REQUEST_408);
        }
        if(exportDto.getIfEmail()!=null&&exportDto.getIfEmail()==1){
            return new RestApiResult<>(RestCode.SUCCESS,"邮件发送成功!");
        }else{
            return new RestApiResult<>(RestCode.SUCCESS,exportVo);
        }
    }


    @ApiOperation(value = "根据id查询简历未填信息",notes = "根据id查询简历未填信息")
    @GetMapping("judge/{id}")
    public  RestApiResult<IfResumeVo> judgeResume(@PathVariable Integer id){
        return new RestApiResult<>(RestCode.SUCCESS,resumeService.judgeResume(id)) ;
    }



    @PostMapping("/export2.0")
    public void export() throws IOException, DocumentException {
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
