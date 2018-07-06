package cn.wind.xboot.controller.intercepotor;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.util.CookieUtil;
import com.xxl.job.admin.core.util.PropertiesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * 权限拦截, 简易版
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class JobPermissionInterceptor extends HandlerInterceptorAdapter {
    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        if (CookieUtil.getValue(request, LOGIN_IDENTITY_KEY) == null || redisTemplate.opsForValue().get("access:" +
                CookieUtil.getValue(request, LOGIN_IDENTITY_KEY)) == null) {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethodAnnotation(PermessionLimit.class) == null || method.getMethodAnnotation(PermessionLimit.class).limit()) {
                response.sendRedirect(request.getContextPath() + "/job/toLogin");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

}
