package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlUserGoodsLogistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 商城订单物流数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-27
 */
public interface MlUserGoodsLogisticsMapper extends BaseMapper<MlUserGoodsLogistics> {

    MlUserGoodsLogistics findOneByConditions(Map<String, Object> map);
}
