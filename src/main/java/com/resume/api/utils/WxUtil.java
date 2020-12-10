package com.resume.api.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

/**
 * @author lz
 */
@Log
public class WxUtil {

    private static final String APPID="wx370c238bee33c668";

    private static final String SECRET="79306d329b2248f4ae6d6733311b27cd";

    /**
     * 根据code获取用户openid
     * @param code
     * @return
     */
    public static String getOpenIdByCode(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String param="appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";
        param = param.replace("APPID",  APPID);
        param = param.replace("SECRET", SECRET);
        param = param.replace("CODE", code);
        String str=HttpRequest.sendGet(url, param);
        log.info("微信获取的数据为>>>>>>>>>>>>>>>>"+str);
        JSONObject jsonObject = JSONObject.parseObject(str);
        if (jsonObject != null) {
            return jsonObject.getString("openid");
        }
        return null;
    }

    /**
     * 根据openId获取用户基本信息
     *
     * @return
     */
    public static JSONObject getUserInfoByOpenId(String accessToken, String openId) {
        String userInfo_url = "https://api.weixin.qq.com/cgi-bin/user/info";
        String param="access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        param = param.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        String str=HttpRequest.sendGet(userInfo_url, param);
        System.out.println(str);
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonObject;
    }


    public static void main(String[] args) {
        //getOpenIdByCode("011M6Nll2HNV864O39ol22xwsO1M6Nlu");
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String param="grant_type=client_credential&appid=APPID&secret=SECRET";
        param = param.replace("APPID",  APPID);
        param = param.replace("SECRET", SECRET);
        //获取access_token
        String accessTokenStr= HttpRequest.sendGet(accessTokenUrl, param);
        JSONObject json=JSONObject.parseObject(accessTokenStr);
        //RedisUtil redis=new RedisUtil(new RedisTemplate<>());
        log.info("定时器任务>>>>>获取到的access_token为>>>>>>"+json.get("access_token"));
        getUserInfoByOpenId(accessTokenStr,"o1_oH5gmBd5sy_5JBx69VjVQiKVY");
    }
}
