package cn.wind.xboot.message.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>Title: SmsSendReq</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SmsSendReq {
    private String phoneNumbers;
    private String templateCode;
    private String templateParam;
    /**
     *提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
     */
    private String outId;
}
