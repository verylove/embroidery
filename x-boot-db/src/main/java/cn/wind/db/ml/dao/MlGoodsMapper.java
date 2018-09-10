package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsMapper extends BaseMapper<MlGoods> {

    List<MlGoods> findAllByConditions(Pagination page, Map<String, Object> map);

    BigDecimal findPostageByIds(List<Long> goodsId);

    List<MlGoods> findAllMlByCondition(Pagination page, Map<String, Object> map);

    @Select("select count(*) from cx_ml_goods where category_id = #{childCategoryId}")
    int countByCategory(@Param("childCategoryId") Long childCategoryId);
}
