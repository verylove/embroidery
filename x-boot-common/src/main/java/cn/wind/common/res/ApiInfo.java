package cn.wind.common.res;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public interface ApiInfo {
    /**
     * 获取返回码
     * @return
     */
    Integer code();

    /**
     * 获取返回信息
     * @return
     */
    String desc();
}
