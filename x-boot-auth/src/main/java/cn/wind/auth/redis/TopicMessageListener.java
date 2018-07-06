package cn.wind.auth.redis;

import cn.hutool.core.util.StrUtil;
import cn.wind.auth.service.AccountNonLockedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author xukk
 * @date 2018/3/14
 */
@Slf4j
@Component
public class TopicMessageListener implements MessageListener {

    @Autowired
    private AccountNonLockedService accountNonLockedService;

    @Override
    public void onMessage(Message message, byte[] pattern) {// 客户端监听订阅的topic，当有消息的时候，会触发该方法
        try {
            // 请使用valueSerializer
            byte[] body = message.getBody();
            byte[] channel = message.getChannel();
            String topic = new String(channel);
            String itemValue = new String(body);
            // 请参考配置文件，本例中key，value的序列化方式均为string。
            log.info("topic:" + topic);
            log.info("itemValue:" + itemValue);
            if ("__keyevent@0__:expired".equals(topic)) {
                if (itemValue.startsWith("locked_time:")) {
                    String[] arr = itemValue.split(StrUtil.COLON);
                    String scope = arr[1];
                    String username = arr[2];
                    accountNonLockedService.resetAccountNonLocked(scope, username);
                }
            }
        } catch (NumberFormatException e) {
            log.warn("接口redis 事件处理失败");
        }

    }
}
