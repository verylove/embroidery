package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoodsSpecifications;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品规格选项数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsSpecificationsService extends IService<MlGoodsSpecifications> {

    List<MlGoodsSpecifications> findAllByGoodsIdAndStatus(Long goodsId, Integer status);
}
