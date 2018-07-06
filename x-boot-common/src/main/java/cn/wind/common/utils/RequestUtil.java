package cn.wind.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author xukk
 * @date 2018/5/4.
 */
public class RequestUtil {
    public static boolean hasEncryptParam(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String aa = enumeration.nextElement();
            if (aa.equals("encrypt")) {
                if (aa.equals("true"))
                    return true;
                else return false;
            }

        }
        return true;
    }
   public static boolean hasData(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            if (enumeration.nextElement().equals("data"))
                return true;
        }
        return false;
    }
    public static boolean hasEncrypt(HttpServletRequest request){
        return hasEncryptParam(request)&&hasEncryptHeader(request);
    }
    public static boolean hasEncryptHeader(HttpServletRequest request) {
        String encrypt=request.getHeader("encrypt");
        if(StringUtils.equalsIgnoreCase(encrypt,"false")){
            return false;
        }else return true;
    }
}
