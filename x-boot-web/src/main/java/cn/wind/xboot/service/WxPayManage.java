package cn.wind.xboot.service;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.xboot.service.app.CXArUserMoneyManage;
import cn.wind.xboot.vo.WxPayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/30 15:43
 * @Description:
 */
@Slf4j
@Service
public class WxPayManage {

    @Autowired
    private WxUtils wxUtils;
    @Autowired
    private CXArUserMoneyManage moneyManage;


    public ApiRes wxPay(WxPayVo vo)throws IOException {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", vo.getAppid());
        parameters.put("mch_id", vo.getMch_id());
        parameters.put("device_info", "WEB"); // 默认 "WEB"
        parameters.put("body", vo.getBody());
        parameters.put("nonce_str", wxUtils.gen32RandomString()); // 32 位随机字符串
        parameters.put("notify_url", vo.getNotify_url());
        parameters.put("out_trade_no", vo.getOut_trade_no());
        parameters.put("total_fee", vo.getTotal_fee());
        parameters.put("spbill_create_ip", vo.getSpbill_create_ip());
        parameters.put("trade_type", "APP");
        parameters.put("sign", wxUtils.createSign(parameters, vo.getSignKey())); // sign 必须在最后
        String result = wxUtils.executeHttpPost(vo.getPlaceUrl(), parameters); // 执行 HTTP 请求，获取接收的字符串（一段 XML）
        return ApiRes.Custom().addData(wxUtils.createSign2(result, vo.getSignKey()));
    }

    @Transactional
    public String callBack(HttpServletRequest request, HttpServletResponse response, WxPayVo vo) throws Exception {
        // 预先设定返回的 response 类型为 xml
        response.setHeader("Content-type", "application/xml");
        // 读取参数，解析Xml为map
        Map<String, String> map = wxUtils.transferXmlToMap(wxUtils.readRequest(request));
        // 转换为有序 map，判断签名是否正确
        boolean isSignSuccess = wxUtils.checkSign(new TreeMap<String, Object>(map), vo.getSignKey());
        if (isSignSuccess) {
            // 签名校验成功，说明是微信服务器发出的数据
            String payNo = map.get("out_trade_no");
            payNo=payNo.substring(0,payNo.length()-6);
            if (moneyManage.hasProcessed(payNo)) // 判断该订单是否已经被接收处理过
                return success();
            // 可在此持久化微信传回的该 map 数据
            //..
            if (map.get("return_code").equals("SUCCESS")) {
                if (map.get("result_code").equals("SUCCESS")) {
                    moneyManage.updateOrder(payNo);  // 支付成功
                } else {
                    moneyManage.failOrder(payNo);    // 支付失败
                }
            }
            return success();
        } else {
            // 签名校验失败（可能不是微信服务器发出的数据）
            return fail();
        }
    }

    String fail() {
        return "<xml>\n" +
                "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                "  <return_msg><![CDATA[]]></return_msg>\n" +
                "</xml>";
    }

    String success() {
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }
}
