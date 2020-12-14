package com.resume.api.utils;

/**
 * JWT所需的各个参数
 * @author lz
 */
public final class ConstantUtils {

    /* JWT token defaults*/
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    /**
     * 角色的key
     **/
    public static final String TOKEN_ROLE_CLAIMS = "rol";
    /**
     * 过期时间24小时
     */
    public final static long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000;

}
