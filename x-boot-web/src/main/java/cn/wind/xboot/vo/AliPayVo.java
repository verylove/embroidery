package cn.wind.xboot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 09:51
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliPayVo {

    private String serverUrl="https://openapi.alipay.com/gateway.do";//支付宝网关（固定）
    private String appId;//APPID即创建应用后生成
    private String privateKey;//开发者应用私钥，由开发者自己生成
    private String format="json";
    private String charset="utf-8";//请求和签名使用的字符编码格式，支持GBK和UTF-8
    private String alipayPublicKey;//支付宝公钥，由支付宝生成
    private String signType="RSA2";//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2

    /**
     * 支付
     */
    private String body;
    private String subject;
    private String timeOutExpress="30m";
    private String productCode="QUICK_MSECURITY_PAY";
    private String notifyUrl;//回调地址
    private String totalAmount;

    private String outTradeNo;//支付时传入的商户订单号，与trade_no必填一个
    private String tradeNo;//支付时返回的支付宝交易号，与out_trade_no必填一个
    /**
     * 退款
     */
    private String outRequestNo;//本次退款请求流水号，部分退款时必传


}
