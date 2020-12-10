package com.resume.api.task;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author lz
 */
@Configuration
@EnableScheduling
public class DeleteFileTask {

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
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void configureTasks() {
        String path="/www/server/tomcat/webapps/office/";


    }
}
