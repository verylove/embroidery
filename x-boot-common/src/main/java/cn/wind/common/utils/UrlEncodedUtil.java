package cn.wind.common.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xukk
 * @date 2018/5/4.
 */
public class UrlEncodedUtil {
    public static String encode(String str) {
        try {
            String encode = URLEncoder.encode(str, "UTF-8");
            return encode;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return str;
    }

    private static String decode(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        try {
            String decode = URLDecoder.decode(str, "UTF-8");
            return decode;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return str;
    }

    /**
     * 解析加密的url
     *
     * @param param
     *            加密的url字符串
     * @return
     */
    public static Map<String, String> parse(String param,Boolean code) {
        Map<String, String> paramMap = new HashMap<String, String>();
        // 解密
        String str="";
        if(code)
            str = decode(param);
        // 判断解密是否错误。
        if (str != null && !str.equals("")) {
            // url参数一般以&符合分隔多个，以=号分隔参数和值
            String[] urls = str.split("&");
            for (String url : urls) {
                // 原因，参数至少有一个 = 号和一个key 所以至少要有3个值,并且肯定要存在=号
                if (!url.equals("") && url.length() >= 3 && url.indexOf("=") > 0) {
                    // 分隔参数
                    String key = url.substring(0, url.indexOf("="));
                    String value = url.substring(url.indexOf("=") + 1);
                    // 如果参数出现多个以第一个为准
                    if (!paramMap.containsKey(key)) {
                        paramMap.put(key, value);
                    }
                }
            }
        }
        return paramMap;
    }

    public static String encode(Map<String, String> map,Boolean code) {
        StringBuilder param = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (index > 0) {
                param.append("&");
            }
            param.append(key);
            param.append("=");
            param.append(value);
            index++;
        }
        if(code)
            return encode(param.toString());
        else return param.toString();
    }
    public static String encode(JsonObject jsonObject, Boolean code){
        StringBuilder param = new StringBuilder();
        int index = 0;
        Iterator i$ = jsonObject.entrySet().iterator();
        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String key = (String)entry.getKey();
            String value = ((JsonElement)entry.getValue()).getAsString();
            if (index > 0) {
                param.append("&");
            }
            param.append(key);
            param.append("=");
            param.append(value);
            index++;
        }
        if(code)
            return encode(param.toString());
        else return param.toString();
    }
}
