package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlUserGoodsSelect;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户选中商品详情数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlUserGoodsSelectMapper extends BaseMapper<MlUserGoodsSelect> {

    MlUserGoodsSelect findOneByConditions(Map<String, Object> map);

    List<MlUserGoodsSelect> findAllByConditons(Map<String, Object> map);
}
