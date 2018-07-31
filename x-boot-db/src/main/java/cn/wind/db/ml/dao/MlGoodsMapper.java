package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

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
}
