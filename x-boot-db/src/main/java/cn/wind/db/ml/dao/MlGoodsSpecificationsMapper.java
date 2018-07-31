package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoodsSpecifications;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品规格选项数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsSpecificationsMapper extends BaseMapper<MlGoodsSpecifications> {

    String RESULT_COLUMN = "id,goods_id,title,select_name,status,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ml_goods_specifications where goods_id = #{goodsId} and status = #{status}")
    List<MlGoodsSpecifications> findAllByGoodsIdAndStatus(@Param("goodsId") Long goodsId, @Param("status") Integer status);
}
