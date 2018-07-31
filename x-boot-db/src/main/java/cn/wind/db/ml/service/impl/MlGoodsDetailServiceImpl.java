package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoodsDetail;
import cn.wind.db.ml.dao.MlGoodsDetailMapper;
import cn.wind.db.ml.service.IMlGoodsDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品配置库存价格数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsDetailServiceImpl extends ServiceImpl<MlGoodsDetailMapper, MlGoodsDetail> implements IMlGoodsDetailService {

    @Override
    public List<MlGoodsDetail> findAllByConditions(Map map) {
        return this.baseMapper.findAllByConditions(map);
    }

    @Override
    public MlGoodsDetail findOneByConditions(Map map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public Integer updateByConditions(Map map) {
        return this.baseMapper.updateByConditions(map);
    }
}
