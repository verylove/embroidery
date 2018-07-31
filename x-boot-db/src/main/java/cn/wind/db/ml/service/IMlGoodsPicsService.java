package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoodsPics;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsPicsService extends IService<MlGoodsPics> {

    List<MlGoodsPics> findAllByConditions(Map<String, Object> map);
}
