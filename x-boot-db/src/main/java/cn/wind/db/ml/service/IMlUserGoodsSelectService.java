package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlUserGoodsSelect;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户选中商品详情数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlUserGoodsSelectService extends IService<MlUserGoodsSelect> {

    MlUserGoodsSelect findOneByConditions(Map<String, Object> map);

    List<MlUserGoodsSelect> findAllByConditons(Map<String, Object> map);
}
