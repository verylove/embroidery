package cn.wind.common.disruptor;

/**
 * 金米袋
 * Created by xukk on 2018/4/2.
 * title:消息包装抽象类
 * desc:
 */
public abstract class ValueWrapper<T> {

    private T value;

    public ValueWrapper() {}

    public ValueWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}