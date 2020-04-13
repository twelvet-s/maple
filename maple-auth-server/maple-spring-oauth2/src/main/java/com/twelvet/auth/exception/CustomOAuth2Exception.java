package com.twelvet.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {

    public CustomOAuth2Exception(String msg) {

        super(msg);

    }
}

