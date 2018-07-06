package cn.wind.common.utils;

import cn.wind.common.res.ApiStatus;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 获取国际化信息
 * @author xukk
 * @date 2018/5/4.
 */
public class MessageUtil {

    private static MessageSource messageSource;

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    /**

     * @param code ：对应messages配置的key.

     * @return

     */

    public static  String getMessage(String code){

        return getMessage(code,null);

    }

    /**

     *

     * @param code ：对应messages配置的key.

     * @param args : 数组参数.

     * @return

     */

    public static String getMessage(String code,Object[] args){

        return getMessage(code, args,"");

    }

    /**

     *

     * @param code ：对应messages配置的key.

     * @param args : 数组参数.

     * @param defaultMessage : 没有设置key的时候的默认值.

     * @return

     */

    public static String getMessage(String code,Object[] args,String defaultMessage){

        //这里使用比较方便的方法，不依赖request.

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(code, args, defaultMessage, locale);

    }

    public static String getMessage(ApiStatus errorStatus){
       return getMessage(errorStatus.name(),null,errorStatus.name());
    }


}