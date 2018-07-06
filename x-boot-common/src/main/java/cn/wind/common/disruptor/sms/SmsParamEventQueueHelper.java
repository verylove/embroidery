package cn.wind.common.disruptor.sms;

import cn.wind.common.disruptor.BaseQueueHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 金米袋
 * Created by xukk on 2018/4/2.
 * title:
 * desc:
 */
@Component
public class SmsParamEventQueueHelper extends BaseQueueHelper<SmsParam, BaseQueueHelper.SmsParamEvent, SmsParamEventHandler> implements InitializingBean {

    private static final int QUEUE_SIZE = 1024;

    private int threadNum;

    @Value("${app.SmsParam.smsMsgEventQueue.threadNum:1}")
    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    protected int getThreadNum() {
        return threadNum;
    }

    @Override
    protected int getQueueSize() {
        return QUEUE_SIZE;
    }

    @Override
    protected Class<SmsParamEvent> eventClass() {
        return SmsParamEvent.class;
    }

    @Override
    protected Class<SmsParamEventHandler> eventHandlerClass() {
        return SmsParamEventHandler.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
