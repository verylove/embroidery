package cn.wind.xboot.vo.app.ml;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/26 16:47
 * @Description: 购物车
 */
@Data
public class MlGoodsShopCartVo {
    private Long id;//goods_select

    private String coverImg;

    private Long goodsId;

    private Long specificationsId;

    private Long modelsId;

    private BigDecimal price;//(总价)

    private Integer num;

    private String name;

    private String specificationsSelectName;

    private String modelsSelectName;

    private Integer status;
}
