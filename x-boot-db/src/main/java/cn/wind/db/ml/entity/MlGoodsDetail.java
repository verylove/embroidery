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
 * 商品配置库存价格数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_goods_detail")
public class MlGoodsDetail extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 规格ID
     */
    private Long specificationsId;
    /**
     * 型号ID
     */
    private Long modelsId;
    /**
     * 库存
     */
    private Long num;
    /**
     * 价格
     */
    private BigDecimal price;

    private Long version=1L;


}
