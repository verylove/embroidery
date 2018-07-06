package cn.wind.common.support.json;

/**
 * @author xukk
 * @date 2018/5/4
 */
public interface GsonStringEnum<E> {
    /**
     * 序列化
     * @return
     */
    String serialize();

    /**
     * 反序列化
     * @param jsonEnum
     * @return
     */
    E deserialize(String jsonEnum);
}
