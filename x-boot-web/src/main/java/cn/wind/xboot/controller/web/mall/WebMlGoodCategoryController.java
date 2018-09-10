package cn.wind.xboot.controller.web.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ml.entity.MlGoodsCategory;
import cn.wind.db.ml.service.IMlGoodsCategoryService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebMlCategoryManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ml.category.AppChildCategoryVo;
import cn.wind.xboot.vo.web.ml.category.AppParentCategoryVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 21:41
 * @Description:
 */
@Api(value = "商城商品分类管理",tags = "商城商品分类管理")
@RestController
@RequestMapping("/webSys/goodCategory")
public class WebMlGoodCategoryController extends BaseController{

    @Autowired
    private IMlGoodsCategoryService goodsCategoryService;
    @Autowired
    private WebMlCategoryManage mlCategoryManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取商城父分类标签")
    @GetMapping("/getParentCategoryByCondition")
    public ApiRes getParentCategoryByCondition(@ModelAttribute AppParentCategoryVo parentCategoryVo,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(parentCategoryVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<MlGoodsCategory> page = goodsCategoryService.findAllParentByCondition(pageVo.initPage(),map);

        Page<AppParentCategoryVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords()==null||page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<AppParentCategoryVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),AppParentCategoryVo.class);
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "父标签编辑")
    @GetMapping("/parentCategoryEdit")
    public ApiRes parentCategoryEdit(Long parentCategoryId){
        MlGoodsCategory mlGoodsCategory = null;
        if(parentCategoryId != null){
            mlGoodsCategory = goodsCategoryService.selectById(parentCategoryId);
        }
        return ApiRes.Custom().success().addData(mlGoodsCategory);
    }

    @ApiOperation(value = "父标签新增或更新")
    @PostMapping("/parentCategoryUpdate")
    public ApiRes parentCategoryUpdate(@ModelAttribute AppParentCategoryVo categoryVo){
        return mlCategoryManage.parentCategoryUpdate(categoryVo);
    }

    @ApiOperation(value = "父标签删除")
    @DeleteMapping("/parentCategoryDelete")
    public ApiRes parentCategoryDelete(Long parentCategoryId){
        return mlCategoryManage.parentCategoryDelete(parentCategoryId);
    }

    @ApiOperation(value = "分页获取商城子分类标签")
    @GetMapping("/getChildCategoryByCondition")
    public ApiRes getChildCategoryByCondition(@ModelAttribute AppChildCategoryVo childCategoryVo,
                                              @ModelAttribute SearchVo searchVo,
                                              @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(childCategoryVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<MlGoodsCategory> page = goodsCategoryService.findAllChildByCondition(pageVo.initPage(),map);

        Page<AppChildCategoryVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords()==null||page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<MlGoodsCategory> goodsCategories = goodsCategoryService.findAllParent();
        Map<Long,MlGoodsCategory> categoryMap = goodsCategories.stream().collect(Collectors.toMap(MlGoodsCategory::getId, Function.identity()));

        List<AppChildCategoryVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),AppChildCategoryVo.class);
        for(AppChildCategoryVo vo:vos){
            vo.setParentName(categoryMap.get(vo.getPid()).getName());
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "子标签编辑")
    @GetMapping("/childCategoryEdit")
    public ApiRes childCategoryEdit(Long childCategoryId){
        AppChildCategoryVo childCategoryVo = null;
        if(childCategoryId != null){
            MlGoodsCategory mlGoodsCategory = goodsCategoryService.selectById(childCategoryId);
            MlGoodsCategory parentCategory = goodsCategoryService.selectById(mlGoodsCategory.getPid());
            BeanUtils.copyProperties(mlGoodsCategory,childCategoryVo);
            childCategoryVo.setParentName(parentCategory.getName());
        }
        return ApiRes.Custom().success().addData(childCategoryVo);
    }

    @ApiOperation(value = "子标签新增或更新")
    @PostMapping("/childCategoryUpdate")
    public ApiRes childCategoryUpdate(@ModelAttribute AppChildCategoryVo categoryVo){
        return mlCategoryManage.childCategoryUpdate(categoryVo);
    }

    @ApiOperation(value = "子标签删除")
    @DeleteMapping("/childCategoryDelete")
    public ApiRes childCategoryDelete(Long childCategoryId){
        return mlCategoryManage.childCategoryDelete(childCategoryId);
    }

    @ApiOperation(value = "父标签")
    @GetMapping("/getAllParentCategory")
    public ApiRes getAllParentCategory(){
        List<MlGoodsCategory> goodsCategories = goodsCategoryService.findAllParent();
        return ApiRes.Custom().success().addData(goodsCategories);
    }

    @ApiOperation(value = "子标签")
    @GetMapping("/getChildCategory")
    public ApiRes getAllParentCategory(Long parent){
        List<MlGoodsCategory> goodsCategories = goodsCategoryService.findAllChildByParent(parent);
        return ApiRes.Custom().success().addData(goodsCategories);
    }
}
