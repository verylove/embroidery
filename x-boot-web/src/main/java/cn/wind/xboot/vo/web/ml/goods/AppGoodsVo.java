package cn.wind.xboot.vo.web.ml.goods;

import cn.wind.db.ml.entity.MlGoodsPics;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/10 09:48
 * @Description:
 */
@Data
public class AppGoodsVo {
    private Long id;

    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格区间 刺币
     */
    private String price;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 邮费
     */
    private BigDecimal postage;
    /**
     * 总库存
     */
    private Long remainNum;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 描述
     */
    private String description;
    /**
     * 产品类别
     */
    private Long categoryId;
    /**
     * 销售量
     */
    private Long sales;
    /**
     * 1-新手必备 0-不是新手必备
     */
    private Integer necessary;
    /**
     * 1-精选商品 0-不是精选商品
     */
    private Integer careselect;
    /**
     * 1-有效 0-失效
     */
    private Integer status;
}
