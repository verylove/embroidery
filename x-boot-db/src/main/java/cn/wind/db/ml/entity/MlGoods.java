package cn.wind.db.ml.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商城商品数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_goods")
public class MlGoods extends AuditEntity {

    private static final long serialVersionUID = 1L;

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
    private BigDecimal postage=new BigDecimal("0.00");
    /**
     * 总库存
     */
    private Long remainNum=0L;
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
    private Integer necessary=0;
    /**
     * 1-精选商品 0-不是精选商品
     */
    private Integer careselect=0;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;


}
