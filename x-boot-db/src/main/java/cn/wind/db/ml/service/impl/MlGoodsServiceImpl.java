package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoods;
import cn.wind.db.ml.dao.MlGoodsMapper;
import cn.wind.db.ml.service.IMlGoodsService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsServiceImpl extends ServiceImpl<MlGoodsMapper, MlGoods> implements IMlGoodsService {

    @Override
    public Page<MlGoods> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public BigDecimal findPostageByIds(List<Long> goodsId) {
        return this.baseMapper.findPostageByIds(goodsId);
    }

    @Override
    public Page<MlGoods> findAllMlByCondition(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllMlByCondition(page,map));
    }

    @Override
    public int countByCategory(Long childCategoryId) {
        return this.baseMapper.countByCategory(childCategoryId);
    }
}
