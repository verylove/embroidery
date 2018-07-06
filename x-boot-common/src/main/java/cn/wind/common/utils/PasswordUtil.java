package cn.wind.common.utils;

import com.google.common.collect.Maps;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Map;

/**
 * @author xukk
 * @date 2018/5/4.
 */
public class PasswordUtil {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static Boolean matches(String rawPassword, String... encodePassword) {
        Map<String, Boolean> map = Maps.newHashMap();
        map.put("flag", false);
        Arrays.asList(encodePassword).forEach(v -> {
            if (encoder.matches(rawPassword, v)) {
                map.put("flag", true);
            }
        });
        return map.get("flag");
    }
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
