package com.resume.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.impl.UidGenerator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonList;


/**
 * @author XiaoWeiBiao
 * @version 1.0
 */
@Configuration
@EntityScan(basePackages = "com.ins.entity")
public class SpringBootConfig {

    @Value("${spring.profiles.active}")
    private String profiles;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {


        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        if (StringUtils.isBlank(profiles)) {
            factory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        }

        factory.setTomcatConnectorCustomizers(singletonList(connector -> {
            connector.setSecure(false);
            connector.setScheme("http");
            connector.setEnableLookups(false);
        }));

        factory.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));

        return factory;

    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        builder.indentOutput(true);
        return builder;
    }


    @Bean
    public Executor taskExecutor() {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("job-pool-%d").build();
        return new ThreadPoolExecutor(0, 100,
                                      60L, TimeUnit.SECONDS
                , new SynchronousQueue<>(), namedThreadFactory);
    }


    /**
     * 注册一个StatViewServlet
     *
     * @return
     */

    @Bean
    public ServletRegistrationBean druidStatViewServle() {

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        //添加初始化参数：initParams

        //白名单：

        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:
        // Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", "192.168.1.0");

        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "adminxiao");
        servletRegistrationBean.addInitParameter("loginPassword", "xiaoweibiao1");

        //是否能够重置数据.

        servletRegistrationBean.addInitParameter("resetEnable", "false");

        return servletRegistrationBean;

    }


    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.

        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.

        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        return filterRegistrationBean;

    }

    @Bean
    public DruidDataSource druidDataSource(@Value("${spring.datasource.driver-class-name}") String driver,
                                           @Value("${spring.datasource.url}") String url,
                                           @Value("${spring.datasource.username}") String username,
                                           @Value("${spring.datasource.password}") String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxActive(200);
        druidDataSource.setMinIdle(5);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        try {
            druidDataSource.setFilters("stat,wall,slf4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }


}
