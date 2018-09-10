package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlGoodsCategory;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商城商品类别数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlGoodsCategoryService extends IService<MlGoodsCategory> {

    Page<MlGoodsCategory> findAllParentByCondition(Page page, Map<String, Object> map);

    Page<MlGoodsCategory> findAllChildByCondition(Page page, Map<String, Object> map);

    List<MlGoodsCategory> findAllParent();

    int countByPid(Long parentCategoryId);

    List<MlGoodsCategory> findAllChildByParent(Long parent);
}
