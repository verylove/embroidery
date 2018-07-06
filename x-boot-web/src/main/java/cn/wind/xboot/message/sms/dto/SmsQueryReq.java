package cn.wind.xboot.message.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>Title: SmsQueryReq</p>
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
public class SmsQueryReq {
    private String phoneNumber;
    private String bizId;
    /**
     * 短信发送日期格式yyyyMMdd,支持最近30天记录查询
     */
    private String sendDate;
    private Long pageSize=10L;
    private Long currentPage=1L;
}
