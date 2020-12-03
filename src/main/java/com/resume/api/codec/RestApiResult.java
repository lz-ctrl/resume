package com.resume.api.codec;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author lz
 * @version 1.0
 */
@ApiModel(value = "rest结果")
public class RestApiResult<T> {

    @ApiModelProperty(value = "结果返回码")
    private int code;
    @ApiModelProperty(value = "结果返回信息")
    private String msg;
    @ApiModelProperty(value = "请求路径")
    private String path;
    @ApiModelProperty(value = "返回结果")
    private T data;


    public RestApiResult() {

    }

    public RestApiResult(int code, String errorMsg) {
        super();
        this.code = code;
        this.msg = errorMsg;
    }

    public RestApiResult(RestCode code) {
        this.setCode(code.code());
        this.setMsg(code.msg());
    }

    public RestApiResult(RestCode code, T data) {
        this.setCode(code.code());
        this.setMsg(code.msg());
        this.setData(data);
    }


    public RestApiResult code(int code) {
        this.code = code;
        return this;
    }


    public RestApiResult msg(String message) {
        this.msg = message;
        return this;
    }

    public RestApiResult path(String path) {
        this.path = path;
        return this;
    }


    public RestApiResult data(T data) {
        this.data = data;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}