package com.resume.api.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.resume.api.codec.RestCode;
import com.resume.api.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * @author lz
 */
public class JwtUtil {


    private static String jwtPublicKey = null;

    private static String userIdAesKey = null;

    private static final Map<String, Object> headerMap = new HashMap<>(1);


    private final static String ROLE_USER = "ROLE_USER";

    static {
        headerMap.put("typ", ConstantUtils.TOKEN_TYPE);
    }

    public synchronized static void setJwtPublicKey(String jwtPublicKey) {
        if (JwtUtil.jwtPublicKey == null) {
            JwtUtil.jwtPublicKey = jwtPublicKey;
        }

    }


    public synchronized static void setUserIdAesKey(String userIdAesKey) {
        if (JwtUtil.userIdAesKey == null) {
            JwtUtil.userIdAesKey = userIdAesKey;
        }
    }


    public static String getUserIdAesKey() {
        return userIdAesKey;
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username    用户名
     * @param expiresAt   过期时间
     * @param authorities
     * @return 加密的token
     */
    public static String sign(Integer userId, String username, String secret, Date expiresAt, Collection<GrantedAuthority> authorities) {

        Algorithm algorithm = Algorithm.HMAC256(DigestUtils.md5Hex(username) + jwtPublicKey);
        return JWT.create()
                  .withHeader(headerMap)
                  .withAudience(CyptoUtils.encode(userId.toString(), userIdAesKey))
                  .withSubject(username)
                  .withExpiresAt(expiresAt)
                  .withClaim(ConstantUtils.TOKEN_ROLE_CLAIMS, String.join(",", authorities.stream().map(authoritie -> authoritie.getAuthority()).collect(Collectors.toList())))
                  .sign(algorithm);

    }

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     */
    public static void verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(DigestUtils.md5Hex(getDecodedJWT(token).getSubject()) + jwtPublicKey);
        JWTVerifier verifier = JWT.require(algorithm)
                                  .build();
        verifier.verify(token);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return DecodedJWT
     */
    public static DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new ServiceException(RestCode.TOKEN_ERROR_508);
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiresAt = getDecodedJWT(token).getExpiresAt();
        return expiresAt.before(new Date());
    }


    public static List<SimpleGrantedAuthority> getUserRolesByToken(DecodedJWT decodedJWT) {
        String role = decodedJWT.getClaim(ConstantUtils.TOKEN_ROLE_CLAIMS).asString();
        return Arrays.stream(role.split(","))
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
    }


}
