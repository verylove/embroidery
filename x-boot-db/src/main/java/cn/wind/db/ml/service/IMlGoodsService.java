package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoods;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsService extends IService<MlGoods> {

    Page<MlGoods> findAllByConditions(Page page, Map<String, Object> map);

    BigDecimal findPostageByIds(List<Long> goodsId);
}
