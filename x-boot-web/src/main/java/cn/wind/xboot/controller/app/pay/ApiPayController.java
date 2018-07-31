package cn.wind.xboot.controller.app.pay;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.MathUtil;
import cn.wind.common.utils.RequestUtil;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ml.entity.MlUserGoodsSelect;
import cn.wind.db.ml.service.IMlUserGoodsSelectService;
import cn.wind.xboot.config.PayConfiguration;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.AliPayManage;
import cn.wind.xboot.service.WxPayManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.service.app.CXArUserMoneyManage;
import cn.wind.xboot.service.app.CXMlGoodsManage;
import cn.wind.xboot.vo.AliPayVo;
import cn.wind.xboot.vo.WxPayVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/26 20:52
 * @Description:
 */
@Slf4j
@Api(value = "支付管理",tags = "支付管理")
@RestController
@RequestMapping("/api")
public class ApiPayController extends AppBaseController{

    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private PayConfiguration payConfiguration;
    @Autowired
    private AliPayManage aliPayManage;
    @Autowired
    private CXArUserMoneyManage moneyManage;
    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;
    @Autowired
    private CXMlGoodsManage goodsManage;
    @Autowired
    private WxPayManage wxPayManage;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXArUserManage userManage;

    @Value("${jsRoot}")
    String jsRoot;

    @ApiOperation(value = "支付宝 商城 支付")
    @ApiImplicitParam(name = "orderNo",value = "订单编号",dataType = "String",required = true,paramType = "query")
    @PostMapping("/app/pay/aliPayForGoods")
    public ApiRes aliPayForGoods(String orderNo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("orderNo",orderNo);
            ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);

            if(moneyRecord==null){
                return ApiRes.Custom().failure(ApiStatus.ORDER_NOT_EXIST);
            }
            if(moneyRecord.getStatus()==2){
                return ApiRes.Custom().failure(ApiStatus.ORDER_ALREADY_FINISH);
            }

            //1.扣减库存
            map = Maps.newHashMap();
            map.put("orderId",moneyRecord.getId());
            List<MlUserGoodsSelect> goodsSelectList = goodsSelectService.findAllByConditons(map);
            goodsManage.stockInMySql(goodsSelectList);

            BigDecimal payAmount = moneyRecord.getAmount().divide(BigDecimal.valueOf(10));

            if(payAmount.compareTo(new BigDecimal("0.00"))<1){
                return ApiRes.Custom().failure(ApiStatus.MONEY_IS_ZERO);
            }

            moneyRecord.setWay(1);//支付宝
            moneyRecordService.updateById(moneyRecord);

            AliPayVo vo = new AliPayVo();
            vo.setAppId(payConfiguration.getAli().getAppId());
            vo.setPrivateKey(payConfiguration.getAli().getPrivateKey());
            vo.setAlipayPublicKey(payConfiguration.getAli().getAlipayPublicKey());
            vo.setBody("商城订单支付");
            vo.setSubject("商城订单支付");
            vo.setOutTradeNo(moneyRecord.getOrderNo()+ MathUtil.getSix());
            vo.setTotalAmount(String.valueOf(payAmount));
            vo.setNotifyUrl(jsRoot+"/api/open/ali_notify");

            return aliPayManage.aliPayApp(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.GOODS_NUM_NOTHING);
        }
    }

    @RequestMapping(value = "/open/ali_notify")
    @ResponseBody
    public String ali_notify(HttpServletRequest request){
        AliPayVo vo = new AliPayVo();
        Map<String,String> requestParams = RequestUtil.getParams(request);
        if(requestParams==null||requestParams.isEmpty()){
            log.warn("ali notify accept error->map is blank");
            return "";
        }
        vo.setAlipayPublicKey(payConfiguration.getAli().getAlipayPublicKey());
        ApiRes response= aliPayManage.aliValid(vo,requestParams);
        if(response.valid()){
            Map<String,String> payMap=(Map)response.getData();
            //trade_status 表示订单交易状态
            String trade_status=payMap.get("trade_status");
            if("TRADE_SUCCESS".equals(trade_status)){
                //交易成功
                String payNo = payMap.get("out_trade_no");
                payNo=payNo.substring(0,payNo.length()-6);
                boolean flag=moneyManage.updateOrder(payNo);
                log.info("订单修改状态："+(flag?"成功":"失败"));
                // 推送消息
//                PushManage.mg.jPushMessage(memberId.toString(),"尊敬的用户您使用支付宝付，充值 "+ totalPay + " 元","钱包充值信息详情","尊敬的用户您使用支付宝支付，充值 "+ totalPay + " 元",null);

            }else {
                //交易暂时未成功 将此状态写进数据库
                //交易信息写入数据库
                return "";
            }
            return "success";
        }else {
            return "";
        }
    }

    @ApiOperation(value = "微信 商城 支付")
    @ApiImplicitParam(name = "orderNo",value = "订单编号",dataType = "String",required = true,paramType = "query")
    @PostMapping("/app/pay/wxPayForGoods")
    public ApiRes wxPayForGoods(String orderNo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("orderNo",orderNo);
            ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);

            if(moneyRecord==null){
                return ApiRes.Custom().failure(ApiStatus.ORDER_NOT_EXIST);
            }
            if(moneyRecord.getStatus()==2){
                return ApiRes.Custom().failure(ApiStatus.ORDER_ALREADY_FINISH);
            }

            //1.扣减库存
            map = Maps.newHashMap();
            map.put("orderId",moneyRecord.getId());
            List<MlUserGoodsSelect> goodsSelectList = goodsSelectService.findAllByConditons(map);
            goodsManage.stockInMySql(goodsSelectList);

            BigDecimal payAmount = moneyRecord.getAmount().multiply(BigDecimal.valueOf(10));//除以10再乘100
            int totalFee=payAmount.intValue();
            if(payAmount.compareTo(new BigDecimal("0.00"))<1){
                return ApiRes.Custom().failure(ApiStatus.MONEY_IS_ZERO);
            }

            moneyRecord.setWay(2);//微信
            moneyRecordService.updateById(moneyRecord);

            WxPayVo vo = new WxPayVo();
            vo.setAppid(payConfiguration.getWx().getAppId());
            vo.setMch_id(payConfiguration.getWx().getMchid());
            vo.setSignKey(payConfiguration.getWx().getSignKey());
            vo.setTrade_type("APP");
            vo.setTotal_fee(String.valueOf(totalFee));
            vo.setBody("订单支付");
            vo.setOut_trade_no(moneyRecord.getOrderNo()+ MathUtil.getSix());
            vo.setSpbill_create_ip("220.191.96.232");
            vo.setNotify_url(jsRoot+"/api/open/wx_notify");

            return wxPayManage.wxPay(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.GOODS_NUM_NOTHING);
        }
    }

    @RequestMapping("/open/wx_notify")
    @ResponseBody
    public String wx_notify(HttpServletRequest request, HttpServletResponse response){
        try {
            WxPayVo vo = new WxPayVo();
            vo.setAppid(payConfiguration.getWx().getAppId());
            vo.setMch_id(payConfiguration.getWx().getMchid());
            vo.setSignKey(payConfiguration.getWx().getSignKey());
            return wxPayManage.callBack(request, response,vo);
        } catch (Exception e) {
            response.setHeader("Content-type", "application/xml");
            return "<xml>\n" +
                    "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                    "  <return_msg><![CDATA[]]></return_msg>\n" +
                    "</xml>";
        }
    }

    @ApiOperation(value = "账户余额 商城 支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo",value = "订单编号",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "payPass",value = "支付密码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/app/pay/balanceForGoods")
    public ApiRes balanceForGoods(String orderNo,String payPass){
        try{
            //验证支付密码
            ArUser user = userService.selectById(getUserId());
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            if(user.getPayPass()==null){
                return ApiRes.Custom().failure(ApiStatus.PAY_PASS_NOT_EXIST);
            }
            if(!user.getPayPass().equals(payPass)){
                return ApiRes.Custom().failure(ApiStatus.PAY_PASS_ERROR);
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("orderNo",orderNo);
            ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);

            if(moneyRecord==null){
                return ApiRes.Custom().failure(ApiStatus.ORDER_NOT_EXIST);
            }
            if(moneyRecord.getStatus()==2){
                return ApiRes.Custom().failure(ApiStatus.ORDER_ALREADY_FINISH);
            }

            //1.扣减库存
            map = Maps.newHashMap();
            map.put("orderId",moneyRecord.getId());
            List<MlUserGoodsSelect> goodsSelectList = goodsSelectService.findAllByConditons(map);
            goodsManage.stockInMySql(goodsSelectList);

            //2.余额
            BigDecimal payAmount = moneyRecord.getAmount();
            if(user.getBalance().compareTo(payAmount)<0){
                return ApiRes.Custom().failure(ApiStatus.ACCOUNT_BALANCE_NOT_ENOUGH);
            }

            if(payAmount.compareTo(new BigDecimal("0.00"))<1){
                return ApiRes.Custom().failure(ApiStatus.MONEY_IS_ZERO);
            }
            moneyRecord.setWay(4);//微信
            moneyRecordService.updateById(moneyRecord);

             if (userManage.balancePay(moneyRecord,user)){
                 return ApiRes.Custom().success();
             }else {
                 return ApiRes.Custom().failure("支付失败");
             }
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.GOODS_NUM_NOTHING);
        }
    }

    @ApiOperation(value = "支付宝 账户 充值")
    @ApiImplicitParam(name = "amount",value = "金额",dataType = "BigDecimal",required = true,paramType = "query")
    @PostMapping("/app/pay/aliTopUp")
    public ApiRes aliTopUp(BigDecimal amount){
        try{
            if(amount.compareTo(new BigDecimal("0.00"))<1){
                return ApiRes.Custom().failure(ApiStatus.MONEY_IS_ZERO);
            }
            ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
            moneyRecord.setUserId(getUserId());
            moneyRecord.setAmount(amount.multiply(BigDecimal.valueOf(10)));
            moneyRecord.setType(1);
            moneyRecord.setModule(1);
            String orderNo = "A1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setOrderNo(orderNo);
            moneyRecord.setStatus(1);
            moneyRecord.setWay(1);//支付宝
            moneyRecordService.insert(moneyRecord);

            AliPayVo vo = new AliPayVo();
            vo.setAppId(payConfiguration.getAli().getAppId());
            vo.setPrivateKey(payConfiguration.getAli().getPrivateKey());
            vo.setAlipayPublicKey(payConfiguration.getAli().getAlipayPublicKey());
            vo.setBody("账户充值");
            vo.setSubject("账户充值");
            vo.setOutTradeNo(moneyRecord.getOrderNo()+ MathUtil.getSix());
            vo.setTotalAmount(String.valueOf(amount.intValue()));
            vo.setNotifyUrl(jsRoot+"/api/open/ali_notify");

            return aliPayManage.aliPayApp(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.TOPUP_ERROR);
        }
    }

    @ApiOperation(value = "微信 账户 充值")
    @ApiImplicitParam(name = "amount",value = "金额",dataType = "BigDecimal",required = true,paramType = "query")
    @PostMapping("/app/pay/wxTopUp")
    public ApiRes wxTopUp(BigDecimal amount){
        try{
            if(amount.compareTo(new BigDecimal("0.00"))<1){
                return ApiRes.Custom().failure(ApiStatus.MONEY_IS_ZERO);
            }
            ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
            moneyRecord.setUserId(getUserId());
            moneyRecord.setAmount(amount.multiply(BigDecimal.valueOf(10)));
            moneyRecord.setType(1);
            moneyRecord.setModule(1);
            String orderNo = "A1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setOrderNo(orderNo);
            moneyRecord.setStatus(1);
            moneyRecord.setWay(2);//支付宝
            moneyRecordService.insert(moneyRecord);

            WxPayVo vo = new WxPayVo();
            vo.setAppid(payConfiguration.getWx().getAppId());
            vo.setMch_id(payConfiguration.getWx().getMchid());
            vo.setSignKey(payConfiguration.getWx().getSignKey());
            vo.setTrade_type("APP");
            vo.setTotal_fee(String.valueOf(amount.multiply(BigDecimal.valueOf(100)).intValue()));
            vo.setBody("账户充值");
            vo.setOut_trade_no(moneyRecord.getOrderNo()+ MathUtil.getSix());
            vo.setSpbill_create_ip("220.191.96.232");
            vo.setNotify_url(jsRoot+"/api/open/wx_notify");

            return wxPayManage.wxPay(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.TOPUP_ERROR);
        }
    }
}
