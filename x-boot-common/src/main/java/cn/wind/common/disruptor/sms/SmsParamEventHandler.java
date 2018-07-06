package cn.wind.common.disruptor.sms;


import cn.wind.common.disruptor.BaseQueueHelper;
import com.lmax.disruptor.WorkHandler;

/**
 * 金米袋
 * Created by xukk on 2018/4/2.
 * title:发送短信并写数据库工作任务.
 * desc:定义了初始化大小、工作线程数量（默认是1）、以及在bean初始化结束时准备好线程。并关联好SmsParamEvent及线程处理动作SmsParamEventHandler。默认的线程等待策略是BlockingWaitStrategy，如果你想极高的响应把CPU占用率弄到50%以上允许覆盖getStrategy使用YieldingWaitStrategy。
 */
public class SmsParamEventHandler implements WorkHandler<BaseQueueHelper.SmsParamEvent> {

    @Override
    public void onEvent(BaseQueueHelper.SmsParamEvent event) throws Exception {
        try {
            SmsParam smsParam = event.getValue();
            //使用注入的短信处理器发送短信，此处省略若干字
        }catch(Throwable tr) {
            tr.printStackTrace();
        }
    }

}
