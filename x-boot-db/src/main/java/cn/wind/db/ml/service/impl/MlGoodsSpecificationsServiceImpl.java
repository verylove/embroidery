package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoodsSpecifications;
import cn.wind.db.ml.dao.MlGoodsSpecificationsMapper;
import cn.wind.db.ml.service.IMlGoodsSpecificationsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品规格选项数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsSpecificationsServiceImpl extends ServiceImpl<MlGoodsSpecificationsMapper, MlGoodsSpecifications> implements IMlGoodsSpecificationsService {

    @Override
    public List<MlGoodsSpecifications> findAllByGoodsIdAndStatus(Long goodsId, Integer status) {
        return this.baseMapper.findAllByGoodsIdAndStatus(goodsId,status);
    }
}
