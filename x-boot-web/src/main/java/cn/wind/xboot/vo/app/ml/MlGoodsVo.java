package cn.wind.xboot.vo.app.ml;

import cn.wind.db.ml.entity.MlGoodsPics;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 14:46
 * @Description:
 */
@Data
public class MlGoodsVo {
    private Long id;

    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格 刺币
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
     * 库存
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
     * 轮播图
     */
    private List<MlGoodsPics> figurePics;
    /**
     * 详情
     */
    private List<MlGoodsPics> detailPics;
}
