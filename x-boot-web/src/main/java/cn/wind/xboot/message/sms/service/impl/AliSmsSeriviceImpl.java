package cn.wind.xboot.message.sms.service.impl;

import cn.wind.common.res.ApiRes;
import cn.wind.xboot.message.sms.dto.SmsQueryReq;
import cn.wind.xboot.message.sms.dto.SmsSendReq;
import cn.wind.xboot.message.sms.service.SmsService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>Title: AliSmsSeriviceImpl</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
@Service
public class AliSmsSeriviceImpl implements SmsService {

    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";
    @Value("${xboot.sms.ali.accessKeyId:}")
    private String accessKeyId;
    @Value("${xboot.sms.ali.accessKeySecret:}")
    private String accessKeySecret;
    @Value("${xboot.sms.ali.regionId:}")
    private String regionId;
    @Value("${xboot.sms.ali.signName:}")
    private String signName;
    @Override
    public ApiRes sendSms(SmsSendReq smsSendReq){

        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint(regionId, regionId, product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(smsSendReq.getPhoneNumbers());
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsSendReq.getTemplateCode());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(smsSendReq.getTemplateParam());

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(smsSendReq.getOutId());

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
                return ApiRes.Map().success().put("BizId",sendSmsResponse.getBizId());
            }else {
                return ApiRes.Custom().failure(sendSmsResponse.getMessage());
            }
        } catch (ClientException e) {
           return ApiRes.Custom().failure(e.getMessage());
        }
    }

    @Override
    public ApiRes query(SmsQueryReq smsQueryReq) {
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint(regionId, regionId, product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象
            QuerySendDetailsRequest request = new QuerySendDetailsRequest();
            //必填-号码
            request.setPhoneNumber(smsQueryReq.getPhoneNumber());
            //可选-流水号
            request.setBizId(smsQueryReq.getBizId());
            //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
            request.setSendDate(smsQueryReq.getSendDate());
            //必填-页大小
            request.setPageSize(smsQueryReq.getPageSize());
            //必填-当前页码从1开始计数
            request.setCurrentPage(smsQueryReq.getCurrentPage());

            //hint 此处可能会抛出异常，注意catch
            querySendDetailsResponse = acsClient.getAcsResponse(request);
            if(querySendDetailsResponse.getCode() != null && querySendDetailsResponse.getCode().equals("OK")){
                //代表请求成功
                return ApiRes.Custom().success().addData(querySendDetailsResponse);
            }else {
                return ApiRes.Custom().failure(querySendDetailsResponse.getMessage());
            }
        } catch (ClientException e) {
            return ApiRes.Custom().failure(e.getMessage());
        }

    }
}
