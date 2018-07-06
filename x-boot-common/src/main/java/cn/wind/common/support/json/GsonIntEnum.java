package cn.wind.common.support.json;

/**
 * @author xukk
 * @date 2018/5/4
 */
public interface GsonIntEnum<E> {
    /**
     * 序列化
     * @return
     */
    int serialize();

    /**
     * 反序列化
     * @param jsonEnum
     * @return
     */
    E deserialize(String jsonEnum);
}
