package cn.wind.db.ml.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商城订单物流数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_user_goods_logistics")
public class MlUserGoodsLogistics extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单状态 1-待发货 2-已发货 3-已收货 0-订单物流作废
     */
    private Integer status=1;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 物流单号
     */
    private String logisticsOrder;
    /**
     * 物流备注
     */
    private String logisticsRemark;


}
