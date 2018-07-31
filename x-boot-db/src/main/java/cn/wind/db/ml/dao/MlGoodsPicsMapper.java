package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlGoodsPics;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlGoodsPicsMapper extends BaseMapper<MlGoodsPics> {

    List<MlGoodsPics> findAllByConditions(Map<String, Object> map);
}
