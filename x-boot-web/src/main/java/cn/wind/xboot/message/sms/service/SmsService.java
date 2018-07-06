package cn.wind.xboot.message.sms.service;

import cn.wind.common.res.ApiRes;
import cn.wind.xboot.message.sms.dto.SmsQueryReq;
import cn.wind.xboot.message.sms.dto.SmsSendReq;

/**
 * <p>Title: SmsService</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
public interface SmsService {
    ApiRes sendSms(SmsSendReq smsSendReq);
    ApiRes query(SmsQueryReq smsQueryReq);
}
