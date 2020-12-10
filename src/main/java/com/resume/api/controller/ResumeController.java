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
import com.resume.api.utils.JavaToPdfHtmlUtil;
import com.resume.api.utils.PdfToImageUtil;
import com.resume.api.utils.SpireUtil;
import com.resume.api.vo.ExportVo;
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
import java.util.List;

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

    /**
     * 上传图片/上传视频
     * @param file
     * @return
     */
    @ApiOperation(value = "头像上传",notes = "头像上传")
    @PostMapping("/img")
    public RestApiResult<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return new RestApiResult<>(RestCode.SUCCESS, resumeService.uploadImage(file));
    }


    @ApiOperation(value = "导出简历",notes = "导出简历")
    @PostMapping("export")
    public RestApiResult<ExportVo> exportResume(@RequestBody ExportDto exportDto) throws IOException, DocumentException {
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
        //用来存储路径的
        String path="";
        //存储HTML信息
        String stringHtml="";
        //最终返回VC
        ExportVo exportVo=new ExportVo();
        switch(key){
            case "PDF":
                //表示导出PDF
                stringHtml = htmlService.firstHtml(resumeId,userId);
                path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,0);
                exportVo.setPath(path);
                break;
            case "PNG":
                //表示导出图片
                stringHtml = htmlService.firstHtml(resumeId,userId);
                path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,1);
                List<String> list= PdfToImageUtil.pdfToImageList(path);
                exportVo.setImgPath(list);
                break;
            case "DOC":
                //表示导出word文档
                stringHtml = htmlService.firstHtml(resumeId,userId);
                path= JavaToPdfHtmlUtil.CreatePDFRenderer(stringHtml,1);
                path= SpireUtil.PDFToWord(path);
                exportVo.setPath(path);
                break;
            default:
                throw new ServiceException(RestCode.BAD_REQUEST_408);
        }
        return new RestApiResult<>(RestCode.SUCCESS,exportVo);
    }

}
