package cn.wind.common.disruptor.sms;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 金米袋
 * Created by xukk on 2018/4/2.
 * title:
 * desc:
 */
@Data
public class SmsParam {

    /**
     * 模板参数
     */
    private Map<String, String> paramMap = new HashMap<String, String>();

    /**
     * 接收人手机号
     */
    private String receiverMobile;

    /**
     * 短信模版
     */
    private String msgTemplate;


}