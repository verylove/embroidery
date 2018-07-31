package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoodsDetail;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品配置库存价格数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsDetailService extends IService<MlGoodsDetail> {

    List<MlGoodsDetail> findAllByConditions(Map map);

    MlGoodsDetail findOneByConditions(Map map);

    Integer updateByConditions(Map map);
}
