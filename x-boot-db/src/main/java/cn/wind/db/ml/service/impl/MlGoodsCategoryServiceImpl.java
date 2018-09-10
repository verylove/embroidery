package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlGoodsCategory;
import cn.wind.db.ml.dao.MlGoodsCategoryMapper;
import cn.wind.db.ml.service.IMlGoodsCategoryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品类别数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlGoodsCategoryServiceImpl extends ServiceImpl<MlGoodsCategoryMapper, MlGoodsCategory> implements IMlGoodsCategoryService {

    @Override
    public Page<MlGoodsCategory> findAllParentByCondition(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllParentByCondition(page,map));
    }

    @Override
    public Page<MlGoodsCategory> findAllChildByCondition(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllChildByCondition(page,map));
    }

    @Override
    public List<MlGoodsCategory> findAllParent() {
        return this.baseMapper.findAllParent();
    }

    @Override
    public int countByPid(Long parentCategoryId) {
        return this.baseMapper.countByPid(parentCategoryId);
    }

    @Override
    public List<MlGoodsCategory> findAllChildByParent(Long parent) {
        return this.baseMapper.findAllChildByParent(parent);
    }
}
