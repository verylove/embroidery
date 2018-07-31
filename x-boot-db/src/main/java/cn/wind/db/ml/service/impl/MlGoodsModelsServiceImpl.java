package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoodsModels;
import cn.wind.db.ml.dao.MlGoodsModelsMapper;
import cn.wind.db.ml.service.IMlGoodsModelsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品型号选项数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsModelsServiceImpl extends ServiceImpl<MlGoodsModelsMapper, MlGoodsModels> implements IMlGoodsModelsService {

    @Override
    public List<MlGoodsModels> findAllByGoodsIdAndStatus(Long goodsId, Integer status) {
        return this.baseMapper.findAllByGoodsIdAndStatus(goodsId,status);
    }
}
