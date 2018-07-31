package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoodsModels;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品型号选项数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsModelsService extends IService<MlGoodsModels> {

    List<MlGoodsModels> findAllByGoodsIdAndStatus(Long goodsId, Integer status);
}
