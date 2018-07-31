package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoodsDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品配置库存价格数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsDetailMapper extends BaseMapper<MlGoodsDetail> {

    List<MlGoodsDetail> findAllByConditions(Map map);

    MlGoodsDetail findOneByConditions(Map map);

    Integer updateByConditions(Map map);
}
