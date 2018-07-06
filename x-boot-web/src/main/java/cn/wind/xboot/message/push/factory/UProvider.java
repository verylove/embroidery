package cn.wind.xboot.message.push.factory;

import cn.wind.xboot.message.push.Provider;
import cn.wind.xboot.message.push.PushConfig;
import cn.wind.xboot.message.push.Pusher;
import cn.wind.xboot.message.push.impl.UPusher;

/**
 * @author xukk
 * @date 2018/5/23
 */
public class UProvider implements Provider {
    @Override
    public Pusher produce(PushConfig.PushConfigDto pushConfigDto) {
        return new UPusher(pushConfigDto);
    }
}
