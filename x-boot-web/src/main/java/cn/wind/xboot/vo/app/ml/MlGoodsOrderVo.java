package cn.wind.xboot.vo.app.ml;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/31 10:15
 * @Description: 用户订单
 */
@Data
public class MlGoodsOrderVo {
    private Long id;

    private String orderNo;

    private List<MlGoodsShopCartVo> vos;

    private Date createTime;

    private Integer status;//1-待支付 2-已支付
}
