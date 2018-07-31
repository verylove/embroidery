package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoodsPics;
import cn.wind.db.ml.dao.MlGoodsPicsMapper;
import cn.wind.db.ml.service.IMlGoodsPicsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsPicsServiceImpl extends ServiceImpl<MlGoodsPicsMapper, MlGoodsPics> implements IMlGoodsPicsService {

    @Override
    public List<MlGoodsPics> findAllByConditions(Map<String, Object> map) {
        return this.baseMapper.findAllByConditions(map);
    }
}
