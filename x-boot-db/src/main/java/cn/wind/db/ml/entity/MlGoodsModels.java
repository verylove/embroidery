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
 * 商品型号选项数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_goods_models")
public class MlGoodsModels extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 标题
     */
    private String title;
    /**
     * 选项名
     */
    private String selectName;
    /**
     * 1-有效 0-失效
     */
    private Integer status;


}
