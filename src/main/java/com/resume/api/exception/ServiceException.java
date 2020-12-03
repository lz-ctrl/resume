package com.resume.api.exception;

import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;

/**
 * @author lz
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -8634700792767837033L;

    public RestCode resultCode;

    public ServiceException(RestCode resultCode) {
        super(resultCode.msg());
        this.resultCode = resultCode;
    }

    public ServiceException(RestCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public RestApiResult<String> result() {
        return new RestApiResult<>(resultCode, getMessage());
    }


    public static ServiceException build(RestCode resultCode) {
        return new ServiceException(resultCode);
    }


    public static ServiceException build(RestCode resultCode, String message) {
        return new ServiceException(resultCode, message);
    }

}
