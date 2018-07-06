package cn.wind.xboot.message.push;

/**
 * @author xukk
 * @date 2018/5/23
 */
public interface Provider {
    Pusher produce(PushConfig.PushConfigDto pushConfigDto);
}
