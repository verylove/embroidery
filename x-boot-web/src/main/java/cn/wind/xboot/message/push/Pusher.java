package cn.wind.xboot.message.push;

import cn.wind.common.res.ApiRes;

/**
 * @author xukk
 */
public interface Pusher {
    ApiRes push(PushDto pushDto);
}
