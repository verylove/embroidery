package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlUserGoodsLogistics;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 商城订单物流数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-27
 */
public interface IMlUserGoodsLogisticsService extends IService<MlUserGoodsLogistics> {

    MlUserGoodsLogistics findOneByConditions(Map<String, Object> map);
}
