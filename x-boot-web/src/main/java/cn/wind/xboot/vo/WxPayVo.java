package cn.wind.xboot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/30 15:35
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayVo {
    private String appid;               //公众账号ID 是
    private String mch_id;              //商户号 是
    private String device_info;         //设备号
    private String nonce_str;           //随机字符串 是
    private String sign;                //签名 是
    private String body;                //商品描述 是
    private String detail;              //商品详情
    private String attach;              //附加数据
    private String out_trade_no;        //商户订单号 是
    private String fee_type;            //货币类型
    private String total_fee;           //总金额 是
    private String spbill_create_ip;    //终端IP 是
    private String time_start;          //交易起始时间
    private String time_expire;         //交易结束时间
    private String goods_tag;           //商品标记
    private String notify_url;          //通知地址 是
    private String trade_type;          //交易类型 是 取值如下：JSAPI，NATIVE，APP
    private String product_id;          //商品ID
    private String openid;              //用户标识
    private String signKey;
    private String out_refund_no;
    private String refund_fee;
    private String refund_fee_type;
    private String json;
    private String 	transaction_id;    //订单号
    // 下单 API 地址
    private String placeUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
