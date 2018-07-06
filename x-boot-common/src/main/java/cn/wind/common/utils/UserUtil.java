package cn.wind.common.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xukk
 * @date 2018/5/4.
 */
public class UserUtil {
    public static Long getCurrentUserId() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return 0L;
        }
        if (ctx.getAuthentication() == null) {
            return 0L;
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return 0L;
        }
        try {
            return Long.valueOf(BeanUtils.getProperty(ctx.getAuthentication().getPrincipal(),"userId"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
