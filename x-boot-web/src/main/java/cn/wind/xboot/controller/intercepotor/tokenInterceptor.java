package cn.wind.xboot.controller.intercepotor;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @Description:拦截请求
 * @create: 2018-01-30 13:51
 */
public class tokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return callbackControl(request,response,handler);

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    /**
     * 拦截返回值
     * @param request
     * @param response
     * @param handler
     * @return
     */
    private boolean callbackControl(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        String token = request.getHeader("token");
        if(token==null) {
            token = request.getParameter("token");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if(token==null){
            mapper.writer()
                    .writeValue(response.getOutputStream(), ApiRes.Custom().failure(ApiStatus.TOKEN_INVALID));
            return false;
        }
        try {
            //判断缓存中是否包含该token
            Long userId= Long.valueOf((token.split("%")[0]));
            String value= (String) redisTemplate.opsForValue().get("embroidery:"+userId);
            if(value==null||!(value.equals(token))){
                mapper.writer()
                        .writeValue(response.getOutputStream(), ApiRes.Custom().failure(ApiStatus.TOKEN_TIMEOUT));
                return false;
            }
            request.setAttribute("userId",userId);
        }catch (Exception e){
            e.printStackTrace();
            mapper.writer()
                    .writeValue(response.getOutputStream(), ApiRes.Custom().failure(ApiStatus.TOKEN_INVALID));
            return false;
        }
        return true;
    }
}
