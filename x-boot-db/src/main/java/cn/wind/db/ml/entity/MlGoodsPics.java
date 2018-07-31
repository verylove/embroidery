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
 * 商品图片数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_goods_pics")
public class MlGoodsPics extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 图片
     */
    private String img;
    /**
     * 1-轮播图 2-商品详情
     */
    private Integer type;


}
