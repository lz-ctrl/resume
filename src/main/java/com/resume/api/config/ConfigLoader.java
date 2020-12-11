package com.resume.api.config;

import com.resume.api.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;


/**
 * @author lz
 * @version 1.0
 */
@Component
public class ConfigLoader implements InitializingBean, ServletContextAware {


    private Logger logger = LoggerFactory.getLogger(ConfigLoader.class);


    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {

        //TODO 改成数据库加载
        logger.info("加载jwt参数!!!");

        JwtUtil.setJwtPublicKey("Am2H+P33qKN7gWLaq0VDxS*luSd/7B0UvFUBS0@1LaNFkeX8HOs%htORnD!8k90G-TZBXI");


        JwtUtil.setUserIdAesKey("0VDxS*lu");
    }
}
