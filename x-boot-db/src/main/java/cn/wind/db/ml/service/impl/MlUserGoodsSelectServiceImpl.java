package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlUserGoodsSelect;
import cn.wind.db.ml.dao.MlUserGoodsSelectMapper;
import cn.wind.db.ml.service.IMlUserGoodsSelectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户选中商品详情数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlUserGoodsSelectServiceImpl extends ServiceImpl<MlUserGoodsSelectMapper, MlUserGoodsSelect> implements IMlUserGoodsSelectService {

    @Override
    public MlUserGoodsSelect findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public List<MlUserGoodsSelect> findAllByConditons(Map<String, Object> map) {
        return this.baseMapper.findAllByConditons(map);
    }

    @Override
    public List<MlUserGoodsSelect> findAllByModels(Map<String, Object> map) {
        return this.baseMapper.findAllByModels(map);
    }

    @Override
    public List<MlUserGoodsSelect> findAllBySpecs(Map<String, Object> map) {
        return this.baseMapper.findAllBySpecs(map);
    }
}
