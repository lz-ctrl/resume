package com.resume.api.security;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.resume.api.codec.RestApiResult;
import com.resume.api.codec.RestCode;
import com.resume.api.utils.ConstantUtils;
import com.resume.api.utils.CyptoUtils;
import com.resume.api.utils.JsonMapper;
import com.resume.api.utils.JwtUtil;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



/**
 * @author lz
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    private JsonMapper jsonMapper = JsonMapper.nonEmptySnakeCaseMapper();

    /**
     * 会从 Spring Security 配置文件那里传过来
     *
     * @param authenticationManager
     * @param userDetailsService
     */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否有 token，并且进行认证
        AuthenticationObj authenticationObj = getAuthentication(request);
        String sendTypeHeader = request.getHeader("sendType");
        /*if (authenticationObj.getRestApiResult() != null) {
            //认证失败
            response.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(jsonMapper.toJson(authenticationObj.getRestApiResult()));
            return;
        } else*/ if (authenticationObj.getUsernamePasswordAuthToken() != null) {
            // 认证成功
            SecurityContextHolder.getContext().setAuthentication(authenticationObj.getUsernamePasswordAuthToken());
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
            return;
        }

    }

    private AuthenticationObj getAuthentication(HttpServletRequest request) {
        AuthenticationObj authenticationObj = new AuthenticationObj();
        String header = request.getHeader(ConstantUtils.TOKEN_HEADER);
        if (header == null || !header.startsWith(ConstantUtils.TOKEN_PREFIX)) {
            return authenticationObj;
        }

        String token = header.split(" ")[1];
        try {
            JwtUtil.verify(token);
        } catch (TokenExpiredException e) {
            authenticationObj.setRestApiResult(new RestApiResult(RestCode.TOKEN_ERROR_501));
            return authenticationObj;
        } catch (Exception e) {
            authenticationObj.setRestApiResult(new RestApiResult(RestCode.TOKEN_ERROR_500));
            return authenticationObj;
        }
        DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
        List<String> encryptIds = decodedJWT.getAudience();
        if (CollectionUtils.isEmpty(encryptIds)) {
            authenticationObj.setRestApiResult(new RestApiResult(RestCode.TOKEN_ERROR_502));
            return authenticationObj;
        }

        Integer tokenUserId;
        try {
            tokenUserId = Integer.valueOf(CyptoUtils.decode(encryptIds.get(0), JwtUtil.getUserIdAesKey()));
        } catch (Exception e) {
            authenticationObj.setRestApiResult(new RestApiResult(RestCode.TOKEN_ERROR_502, e.getMessage()));
            return authenticationObj;
        }
        UsernamePasswordAuthenticationToken nt = new UsernamePasswordAuthenticationToken(tokenUserId,
                                                                                         null,
                                                                                         JwtUtil.getUserRolesByToken(decodedJWT));
        authenticationObj.setUsernamePasswordAuthToken(nt);
        return authenticationObj;
    }
}
