package cn.wind.auth.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: CustomWebResponseExceptionTranslator</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/7/2
 */
@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if(e instanceof OAuth2Exception){
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            CustomOauthException customOauthException=new CustomOauthException(oAuth2Exception.getMessage());
            customOauthException.setStatus(oAuth2Exception.getHttpErrorCode());
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(customOauthException);
        }
        else {
            CustomOauthException customOauthException=new CustomOauthException(e.getMessage());
            customOauthException.setStatus(400);
            return ResponseEntity
                    .status(400)
                    .body(customOauthException);
        }

    }
}