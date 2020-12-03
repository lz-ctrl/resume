package com.resume.api.security;
import com.resume.api.codec.RestCode;
import com.resume.api.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author lz
 */
public class AuthenticationUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationUtil.class);

    public static void mustIsSelf(Integer id) {
        Integer tokenUserId = getCurrentUserId();
        if (!id.equals(tokenUserId)) {
            logger.error("toke id:" + tokenUserId, "实际id:" + id);
            throw ServiceException.build(RestCode.TOKEN_ERROR_505);
        }
    }


    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (Integer) authentication.getPrincipal();
            }
        }

        return null;

    }


}
