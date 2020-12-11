package com.resume.api.utils;

/**
 * 进度原因临时加这个，后面可以从数据库加载，后台管理系统进行修改
 *
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
     * 过期时间10分钟
     */
    public final static long TOKEN_EXPIRE_TIME = 10 * 60 * 1000;

    /*提示*/
    /**
     * 删除通用提示语
     */
    public static final String SUCCESS_DEL = "删除成功!";


    public static final String NOT_NULL = "不能为空!";

    public static final String FORMAT_ERROR = "格式错误!";


    public static final String WXLOGIN_ERROR = "errmsg";


    public final static String LOGIN_LOG_CACHE = "login_log";

    /**
     * 过期时间1分钟
     */
    public final static int SMS_EXPIRE_TIME = 60;


    /**
     * 一天的秒数
     */
    public final static int SECOND_OF_DAY = 86400;

    /**
     * 一天最大的短信发送量
     */
    public final static int DAY_MAX_SMS = 5;


    /**
     * 连续签到多少天送补签卡
     */
    public final static int C_SIGN_DAYS = 30;


    /**
     * 个人风采的图片名前缀
     */
    public final static String MY_STYLE_IMGS_PREFIX = "my_style_";


    /**
     * license
     * 公司营业执照
     */
    public final static String LICENSE_IMGS_PREFIX = "license_";

    /**
     * 公司图片
     */
    public final static String COMPANY_IMGS_PREFIX = "company_";


    /**
     * 跟读图片
     */
    public final static String READ_FOLLOW_IMGS_PREFIX = "readfollow_";

    /**
     * 课程图片
     */
    public final static String LIVE_COURSE_IMGS_PREFIX = "course_";



    /**
     * 课程图片
     */
    public final static String LIVE_VIDEO_IMGS_PREFIX = "video_";

    /**
     * 实训图片
     */
    public final static String PRACTICE_IMGS_PREFIX = "practice_";

    /**
     * 轮播图
     */
    public final static String BANNER_IMGS_PREFIX = "banner_";

    /**
     * 音频文件
     */
    public final static String AUDIO_FILE_PREFIX = "audio_";


    public final static String PHONE_REGEXP = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[1|8|9])|(16[6]))\\d{8}$";


    public final static String EMAIL_REGEXP = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 补签卡唯一约束key
     */
    public final static String SHARE_RE_SIGN_CARD_KEY = "re_sign_card_";


    public final static String SMS_LOGIN_KEY = "sms_login_key";

    public final static String SMS_BIND_KEY = "sms_bind_key";

}
