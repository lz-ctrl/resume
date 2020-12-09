package com.resume.api.exception;


import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lz
 * @version 1.0
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * 覆盖handleExceptionInternal这个汇总处理方法，将响应数据替换为我们的{@link }即可
     *
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        RestApiResult restApiResult;
        if (ex instanceof MethodArgumentNotValidException) {
            // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
            MethodArgumentNotValidException bindException = (MethodArgumentNotValidException) ex;
            FieldError fieldError = bindException.getBindingResult().getFieldError();
            // 生成返回结果
            restApiResult = new RestApiResult(RestCode.VALID_ERROR_100);
            restApiResult.setData(fieldError.getDefaultMessage());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + methodArgumentTypeMismatchException.getParameter().getMethod().getName() + ",参数：" +
                                 methodArgumentTypeMismatchException.getName());
            restApiResult = new RestApiResult(RestCode.VALID_ERROR_100);
            restApiResult.setData("参数转换失败!");
        } else {
            String message;
            if (ex.getCause() == null) {
                message = ex.getMessage();
            } else {
                message = ex.getCause().getMessage();
            }
            restApiResult = new RestApiResult(RestCode.EX_HANDLER_300).code(status.value()).msg(message.split("\n")[0]);
        }
        logger.error(String.format("%s:%s", ((ServletWebRequest) request).getRequest().getServletPath(), ex.getMessage()), ex);
        return new ResponseEntity<>(restApiResult, status);
    }

    @ExceptionHandler(value = {ServiceException.class})
    public final RestApiResult handleServiceException(ServiceException ex, HttpServletRequest request) {
        String path = getPath(request);
        logger.error(String.format("%s:%s", path, ex.getMessage()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        RestApiResult result = ex.result();
        result.path(path);
        return result;
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public final RestApiResult handleGeneralAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String path = getPath(request);
        logger.error(String.format("%s:%s", path, ex.getMessage()));
        return new RestApiResult(RestCode.TOKEN_ERROR_504);

    }

    @ExceptionHandler(value = {Exception.class})
    public final RestApiResult handleGeneralException(Exception ex, HttpServletRequest request) {
        String path = getPath(request);
        logger.error(String.format("%s:%s", path, ex.getMessage()), ex);
        RestApiResult result;
        if (ex instanceof DataAccessException) {
            result = new RestApiResult(RestCode.EX_HANDLER_302).path(getPath(request));
        } else {
            result = new RestApiResult(RestCode.EX_HANDLER_301).msg(ex.getMessage())
                                                               .path(getPath(request));
        }

        return result;
    }


    private String getPath(HttpServletRequest request) {
        return request.getQueryString() != null ? (request.getRequestURI() + "?" + request.getQueryString()) : request.getRequestURI();
    }


}
