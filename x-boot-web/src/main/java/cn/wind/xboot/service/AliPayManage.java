package cn.wind.xboot.service;

import cn.wind.common.res.ApiRes;
import cn.wind.xboot.vo.AliPayVo;
import cn.wind.xboot.vo.WxPayVo;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 09:54
 * @Description:
 */
@Slf4j
@Service
public class AliPayManage {

    /**
     * 支付包 支付
     * @param vo
     * @return
     * @throws Exception
     */
    public ApiRes aliPayApp(AliPayVo vo) throws Exception{
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(vo.getServerUrl(), vo.getAppId(), vo.getPrivateKey(), vo.getFormat(), vo.getCharset(), vo.getAlipayPublicKey(), vo.getSignType());
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(vo.getBody());
        model.setSubject(vo.getSubject());
        model.setOutTradeNo(vo.getOutTradeNo());
        model.setTimeoutExpress(vo.getTimeOutExpress());
        model.setTotalAmount(vo.getTotalAmount());
        model.setProductCode(vo.getProductCode());
        request.setBizModel(model);
        request.setNotifyUrl(vo.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            log.info("ali pay response body->{}",response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            if(response.isSuccess()){
                return ApiRes.Custom().addData(response);
            }else {
                return ApiRes.Custom().failure(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ApiRes.Custom().failure(e.getMessage());
        }
    }

    /**
     * 支付宝回调 验签
     * @param vo
     * @param requestParams
     * @return
     */
    public ApiRes aliValid(AliPayVo vo,Map requestParams){
        try {
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(requestParams, vo.getAlipayPublicKey(), vo.getCharset(),vo.getSignType());
            if(flag){
                return ApiRes.Custom().addData(requestParams);
            }else {
                return ApiRes.Custom().failure("ali 校验失败!");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.warn("AlipayApiException 错误:{}", e.getErrMsg());
            return ApiRes.Custom().failure(e.getErrMsg());
        }
    }
}
