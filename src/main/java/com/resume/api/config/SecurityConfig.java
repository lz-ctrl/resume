package com.resume.api.config;


import com.resume.api.security.JwtAuthenticationFilter;
import com.resume.api.security.JwtAuthorizationFilter;
import com.resume.api.security.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 开启 Security
 * 开启注解配置支持
 *
 * @author lz
 * @version 1.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启跨域
        http.cors()
            .and()
            // security 默认 csrf 是开启的，我们使用了 token ，这个也没有什么必要了
            .csrf().disable()
            .authorizeRequests()
            //这里放行登录跟获取token的接口
            .antMatchers("/user/login").permitAll()
            .antMatchers("/user/token").permitAll()
            .anyRequest().authenticated()
            .and()
            // 添加自己编写的两个过滤器
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsServiceImpl))
            // 前后端分离是 STATELESS，故 session 使用该策略
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 此处配置 AuthenticationManager，并且实现缓存
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        security 默认鉴权完成后会把密码抹除，但是这里我们使用用户的密码来作为 JWT 的生成密钥，
        如果被抹除了，在对 JWT 进行签名的时候就拿不到用户密码了，故此处关闭了自动抹除密码。
         */
        auth.eraseCredentials(false);
        auth.userDetailsService(userDetailsServiceImpl);
    }
}
