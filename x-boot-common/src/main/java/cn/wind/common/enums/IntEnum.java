package cn.wind.common.enums;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
interface IntEnum<E extends Enum<E>> {
    default int getIntValue() {
        return 0;
    }
}