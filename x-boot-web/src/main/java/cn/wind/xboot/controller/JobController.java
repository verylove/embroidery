package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.util.CookieUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: JobController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/29
 */
@RestController
@Api(value = "job",tags = "任务管理")
@RequestMapping("job")
public class JobController {
    @Autowired
    private RedisTemplate redisTemplate;
    private String LOGIN_IDENTITY_KEY="XXL_JOB_LOGIN_IDENTITY";
    @RequestMapping(value="login", method= RequestMethod.POST)
    @ResponseBody
    @PermessionLimit(limit=false)
    public ApiRes loginDo(HttpServletRequest request, HttpServletResponse response, @RequestParam String accessToken){
        // valid
        String indentityInfo = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if(indentityInfo!=null){
            Object o=redisTemplate.opsForValue().get("access:"+indentityInfo);
            if(o!=null){
                return ApiRes.Custom().success();
            }
        }
        Object o1=redisTemplate.opsForValue().get("access:"+accessToken);

        if(o1!=null){
            CookieUtil.set(response, LOGIN_IDENTITY_KEY, accessToken, false);
            return ApiRes.Custom().success();
        }else {
            return ApiRes.Custom().failure(401,"登录异常");
        }

    }
}
