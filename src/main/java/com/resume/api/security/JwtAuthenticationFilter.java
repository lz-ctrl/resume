package com.resume.api.security;


import com.alibaba.fastjson.JSONObject;
import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.exception.ServiceException;
import com.resume.api.utils.ConstantUtils;
import com.resume.api.utils.JsonMapper;
import com.resume.api.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * @author lz
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

    private JsonMapper jsonMapperSnakeCase = JsonMapper.nonEmptySnakeCaseMapper();


    private final static String ENG_REGEX = ".*[a-zA-z].*";

    /**
     * 过滤器一定要设置 AuthenticationManager，所以此处我们这么编写，这里的 AuthenticationManager
     * 我会从 Security 配置的时候传入
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        /*
        运行父类 UsernamePasswordAuthenticationFilter 的构造方法，能够设置此滤器指定
        方法为 POST [\login]
        */
        // super();
        super.setFilterProcessesUrl("/user/token");
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从请求的 POST 中拿取 code 和 password 两个字段进行登入
        String username = request.getParameter("code");
        // 小程序不需要密码，直接默认为123
        String password = request.getParameter("password");
        if (StringUtils.isBlank(password)) {
            password = "123";
        }
        UsernamePasswordAuthenticationToken token;
        try {
            token = new UsernamePasswordAuthenticationToken(username, URLDecoder.decode(password, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServiceException(RestCode.TOKEN_ERROR_506);
        }
        // 设置一些客户 IP 啥信息，后面想用的话可以用，虽然没啥用
        // setDetails(request, token);
        // 交给 AuthenticationManager 进行鉴权

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * 鉴权成功进行的操作，我们这里设置返回加密后的 token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        handleResponse(request, response, authResult, null);
    }

    /**
     * 鉴权失败进行的操作
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        handleResponse(request, response, null, failed);
    }

    private void handleResponse(HttpServletRequest request, HttpServletResponse response, Authentication authResult, AuthenticationException failed) throws IOException, ServletException {
        RestApiResult restApiResult;
        response.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        System.out.println(authResult);
        if (authResult != null) {
            restApiResult = new RestApiResult(RestCode.SUCCESS);
            // 处理登入成功请求
            SecurityUser user = (SecurityUser) authResult.getPrincipal();
            Date expireDate = new Date(System.currentTimeMillis() + ConstantUtils.TOKEN_EXPIRE_TIME);

            String token = ConstantUtils.TOKEN_PREFIX + JwtUtil.sign(user.getId(), user.getUsername(),
                                                                     user.getPassword(), expireDate, user.getAuthorities());
            restApiResult.setMsg("token获取成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("token_time", expireDate);
            //TODO 可以添加更多返回信息
            restApiResult.setData(jsonObject);
        } else {
            restApiResult = new RestApiResult(RestCode.BAD_REQUEST_401);
            // 处理登入失败请求
            String failMsg = failed.getMessage();
            if (StringUtils.isBlank(failMsg) || failMsg.matches(ENG_REGEX)) {
                failMsg = "token获取失败";
            }
            restApiResult.setMsg(failMsg);
            restApiResult.setData(null);
        }
        response.getWriter().write(jsonMapperSnakeCase.toJson(restApiResult));
    }
}
