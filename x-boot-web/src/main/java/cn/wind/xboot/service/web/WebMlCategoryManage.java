package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ml.entity.MlGoodsCategory;
import cn.wind.db.ml.service.IMlGoodsCategoryService;
import cn.wind.db.ml.service.IMlGoodsService;
import cn.wind.xboot.vo.web.ml.category.AppChildCategoryVo;
import cn.wind.xboot.vo.web.ml.category.AppParentCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/9 19:43
 * @Description:
 */
@Service
public class WebMlCategoryManage {

    @Autowired
    private IMlGoodsCategoryService categoryService;
    @Autowired
    private IMlGoodsService goodsService;

    @Transactional
    public ApiRes parentCategoryUpdate(AppParentCategoryVo categoryVo) {
        if(categoryVo.getId() == null || categoryVo.getId().compareTo(0L) <=0){//新增
            MlGoodsCategory goodsCategory = new MlGoodsCategory();
            goodsCategory.setPid(0L);
            goodsCategory.setName(categoryVo.getName());
            goodsCategory.setCoverImg(categoryVo.getCoverImg());
            categoryService.insert(goodsCategory);
        }else {
            MlGoodsCategory goodsCategory = categoryService.selectById(categoryVo.getId());
            goodsCategory.setName(categoryVo.getName());
            goodsCategory.setCoverImg(categoryVo.getCoverImg());
            categoryService.updateById(goodsCategory);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes childCategoryUpdate(AppChildCategoryVo categoryVo) {
        if(categoryVo.getId() == null || categoryVo.getId().compareTo(0L) <=0){//新增
            MlGoodsCategory goodsCategory = new MlGoodsCategory();
            goodsCategory.setPid(categoryVo.getPid());
            goodsCategory.setName(categoryVo.getName());
            goodsCategory.setCoverImg(categoryVo.getCoverImg());
            categoryService.insert(goodsCategory);
        }else {
            MlGoodsCategory goodsCategory = categoryService.selectById(categoryVo.getId());
            goodsCategory.setPid(categoryVo.getPid());
            goodsCategory.setName(categoryVo.getName());
            goodsCategory.setCoverImg(categoryVo.getCoverImg());
            categoryService.updateById(goodsCategory);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes parentCategoryDelete(Long parentCategoryId) {
        MlGoodsCategory goodsCategory = categoryService.selectById(parentCategoryId);
        if(goodsCategory != null){
            int count = categoryService.countByPid(parentCategoryId);
            if(count <= 0){
                categoryService.deleteById(parentCategoryId);
            }else {
                return ApiRes.Custom().failure("该标签下存在子标签");
            }
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes childCategoryDelete(Long childCategoryId) {
        MlGoodsCategory goodsCategory = categoryService.selectById(childCategoryId);
        if(goodsCategory != null){
            int count = goodsService.countByCategory(childCategoryId);
            if(count <= 0){
                categoryService.deleteById(childCategoryId);
            }else {
                return ApiRes.Custom().failure("该标签下存在商品");
            }
        }
        return ApiRes.Custom().success();
    }
}
