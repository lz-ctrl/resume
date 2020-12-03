package com.resume.api.codec;


/**
 * @author lz
 * @version 1.0
 */
public enum RestCode {


    /**
     * 成功
     */
    SUCCESS(200, "成功"),


    //100是参数校验类型的错误
    VALID_ERROR_100(100, "参数校验错误"),

    //300
    EX_HANDLER_300(300, null),
    EX_HANDLER_301(301, null),
    EX_HANDLER_302(302, "数据库异常!"),
    EX_HANDLER_303(303, "操作频繁,请稍后重试!"),

    //400是业务类型错误
    //400 = 账号余额不足!
    BAD_REQUEST_400(400, "请求出错!"),
    BAD_REQUEST_401(401, "用户名或者密码错误!"),
    BAD_REQUEST_402(402, "微信登录失败!"),
    BAD_REQUEST_403(403, "短信发送失败!"),
    BAD_REQUEST_404(404, "60秒以内不能重复发送!"),
    BAD_REQUEST_405(405, "短信发送过于频繁!"),
    BAD_REQUEST_406(406, "图片上传失败!"),
    BAD_REQUEST_407(407, "图片删除失败!"),
    BAD_REQUEST_408(408, "个人风采最多8张图片!"),
    BAD_REQUEST_409(409, "所需数据不存在!"),
    BAD_REQUEST_413(413, "该订单已经使用!"),
    BAD_REQUEST_414(414, "该论文正在准备提交检测系统!"),
    BAD_REQUEST_415(415, "商品已存在，换个商品吧！"),
    BAD_REQUEST_416(416, "账上仟币不足!"),
    BAD_REQUEST_417(417, "订单已过期!"),
    BAD_REQUEST_418(418, "请先设置支付密码!"),
    BAD_REQUEST_419(419, "支付密码错误!"),

    //500是token类型错误
    TOKEN_ERROR_500(500, "token校验错误!"),
    TOKEN_ERROR_501(501, "token过期!"),
    TOKEN_ERROR_502(502, "token非法!"),
    TOKEN_ERROR_503(503, "用户不存在!"),
    TOKEN_ERROR_504(504, "你没有访问权限!"),
    TOKEN_ERROR_505(505, "用户信息不匹配!"),
    TOKEN_ERROR_506(506, "密码解码错误!"),
    TOKEN_ERROR_507(507, "用户名或者密码为空!"),
    TOKEN_ERROR_508(508, "token解密失败!"),
    TOKEN_ERROR_509(509, "token生成失败!"),
    TOKEN_ERROR_510(510, "用户登录信息不存在!");

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    private int code;
    private String msg;
}  