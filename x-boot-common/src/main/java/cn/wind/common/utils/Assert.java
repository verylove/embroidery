package cn.wind.common.utils;

import cn.wind.common.res.ApiException;
import com.google.common.base.Preconditions;

/**
 * 断言
 *
 * @author xukk
 * @date 2018/5/4
 */
public class Assert {
    /**
     * 功能描述：检查boolean是否为真。 用作方法中检查参数
     *
     * @param expression
     * @param message
     * @param code
     * @throws
     */
    public  static void checkArgument(boolean expression, String message, Integer code) throws ApiException {

        try {
            Preconditions.checkArgument(expression);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException(code, message);
        }
    }

    /**
     * 功能描述：检查value不为null， 直接返回value；
     *
     * @param o
     * @param message
     * @param code
     * @throws
     */
    public   static void checkNotNull(Object o, String message, Integer code)
            throws ApiException {
        try {
            Preconditions.checkNotNull(o);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException(code, message);
        }
    }

    /**
     * 功能描述：检查对象的一些状态，不依赖方法参数
     *
     * @param expression
     * @param message
     * @param code
     * @throws
     */
    public   static void checState(boolean expression, String message, Integer code)
            throws ApiException {
        try {
            Preconditions.checkState(expression);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException(code, message);
        }
    }
    /**
     * 功能描述：检查boolean是否为真。 用作方法中检查参数
     *
     * @param expression
     * @param message
     * @param code
     * @throws
     */
    public  static void checkArgument(boolean expression, String message) throws ApiException {

        try {
            Preconditions.checkArgument(expression);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException( message);
        }
    }

    /**
     * 功能描述：检查value不为null， 直接返回value；
     *
     * @param o
     * @param message
     * @param code
     * @throws
     */
    public   static void checkNotNull(Object o, String message)
            throws ApiException {
        try {
            Preconditions.checkNotNull(o);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException(message);
        }
    }

    /**
     * 功能描述：检查对象的一些状态，不依赖方法参数
     *
     * @param expression
     * @param message
     * @param code
     * @throws
     */
    public   static void checState(boolean expression, String message)
            throws ApiException {
        try {
            Preconditions.checkState(expression);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApiException(message);
        }
    }
}
