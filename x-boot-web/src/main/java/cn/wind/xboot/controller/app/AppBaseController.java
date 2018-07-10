package cn.wind.xboot.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 16:57
 * @Description:
 */
public class AppBaseController {
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    protected HttpServletRequest request;

    /**
     * 获取当前用户Id
     * @return
     */
    public Long getUserId() {
        return request.getAttribute("userId")!=null?Long.parseLong(request.getAttribute("userId").toString()):null;
    }
}
