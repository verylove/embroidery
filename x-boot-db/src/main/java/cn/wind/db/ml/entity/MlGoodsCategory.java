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
 * 商城商品类别数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_goods_category")
public class MlGoodsCategory extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类别名
     */
    private String name;
    /**
     * 上一级ID 父级为0
     */
    private Long pid=0L;
    /**
     * 图片
     */
    private String coverImg;


}
