package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlUserGoodsLogistics;
import cn.wind.db.ml.dao.MlUserGoodsLogisticsMapper;
import cn.wind.db.ml.service.IMlUserGoodsLogisticsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 商城订单物流数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-27
 */
@Service
public class MlUserGoodsLogisticsServiceImpl extends ServiceImpl<MlUserGoodsLogisticsMapper, MlUserGoodsLogistics> implements IMlUserGoodsLogisticsService {

    @Override
    public MlUserGoodsLogistics findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
