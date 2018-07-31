package cn.wind.db.ml.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.wind.mybatis.common.AuditEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户选中商品详情数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_user_goods_select")
public class MlUserGoodsSelect extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品规格
     */
    private Long specificationsId;
    /**
     * 商品型号ID
     */
    private Long modelsId;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 商品数量
     */
    private Integer num;
    /**
     * 1-购物车 2-已生成订单
     */
    private Integer type;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 1-有效 0-购物车选项失效
     */
    private Integer status=1;

}
