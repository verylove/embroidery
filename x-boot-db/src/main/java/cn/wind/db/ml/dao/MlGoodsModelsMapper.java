package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoodsModels;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品型号选项数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsModelsMapper extends BaseMapper<MlGoodsModels> {

    String RESULT_COLUMN = "id,goods_id,title,select_name,status,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ml_goods_models where goods_id = #{goodsId} and status = #{status}")
    List<MlGoodsModels> findAllByGoodsIdAndStatus(Long goodsId, Integer status);
}
