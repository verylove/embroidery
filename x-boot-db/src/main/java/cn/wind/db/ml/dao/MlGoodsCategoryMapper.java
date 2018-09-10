package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoodsCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品类别数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsCategoryMapper extends BaseMapper<MlGoodsCategory> {

    List<MlGoodsCategory> findAllParentByCondition(Pagination page, Map<String, Object> map);

    List<MlGoodsCategory> findAllChildByCondition(Pagination page, Map<String, Object> map);

    List<MlGoodsCategory> findAllParent();

    @Select("select count(*) from cx_ml_goods_category where pid = #{parentCategoryId}")
    int countByPid(@Param("parentCategoryId") Long parentCategoryId);

    List<MlGoodsCategory> findAllChildByParent(Long pid);
}
