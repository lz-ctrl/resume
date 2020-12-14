package com.resume.api.task;


import com.alibaba.fastjson.JSONObject;
import com.resume.api.dao.UserMapper;
import com.resume.api.utils.FileUtil;
import com.resume.api.utils.HttpRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author lz
 */
@Configuration
@EnableScheduling
@Log
public class DeleteFileTask {

    @Autowired
    UserMapper userMapper;

    private static final String APPID="wx370c238bee33c668";

    private static final String SECRET="79306d329b2248f4ae6d6733311b27cd";

//    每隔5秒执行一次：*/5 * * * * ?
//
//    每隔1分钟执行一次：0 */1 * * * ?
//
//    每天23点执行一次：0 0 23 * * ?
//
//    每天凌晨1点执行一次：0 0 1 * * ?
//
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//
//    每月最后一天23点执行一次：0 0 23 L * ?
//
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//
//    在26分、29分、33分执行一次：0 26,29,33 * * * ?
//
//    每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

    /**
     * 添加定时任务
     * 每天凌晨1点删除文件夹下所有内容
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void configureTasks() {
        String path="/www/server/tomcat/webapps/office";
        String imgPath="/www/server/tomcat/webapps/img";
        FileUtil.delAllFile(path);
        FileUtil.delAllFile(imgPath);
    }

    //每六十分钟执行一次

    @Scheduled(cron = "0 */60 * * * ?")
    public void updateaccess_token() {
        //获取access_token
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String param="grant_type=client_credential&appid=APPID&secret=SECRET";
        param = param.replace("APPID",  APPID);
        param = param.replace("SECRET", SECRET);
        //获取access_token
        String accessTokenStr= HttpRequest.sendGet(accessTokenUrl, param);
        JSONObject json=JSONObject.parseObject(accessTokenStr);
        log.info("定时器任务>>>>>获取到的access_token为>>>>>>"+json.get("access_token"));
        userMapper.updateWxKey(json.get("access_token").toString());
    }
}
