package com.resume.api.security;



import com.resume.api.codec.RestApiResult;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author lz
 */
public class AuthenticationObj {

    private UsernamePasswordAuthenticationToken usernamePasswordAuthToken;


    private RestApiResult restApiResult;


    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthToken() {
        return usernamePasswordAuthToken;
    }

    public void setUsernamePasswordAuthToken(UsernamePasswordAuthenticationToken usernamePasswordAuthToken) {
        this.usernamePasswordAuthToken = usernamePasswordAuthToken;
    }

    public RestApiResult getRestApiResult() {
        return restApiResult;
    }

    public void setRestApiResult(RestApiResult restApiResult) {
        this.restApiResult = restApiResult;
    }
}
