package cn.wind.xboot.controller.web.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ml.entity.*;
import cn.wind.db.ml.service.*;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebMlGoodsDetailManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ml.goods.AppGoodsVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/10 09:42
 * @Description:
 */
@Api(value = "商城商品管理",tags = "商城商品管理")
@RestController
@RequestMapping("/webSys/mallGoods")
public class WebMlGoodsController extends BaseController{

    @Autowired
    private IMlGoodsService goodsService;
    @Autowired
    private IMlGoodsPicsService goodsPicsService;
    @Autowired
    private IMlGoodsCategoryService categoryService;
    @Autowired
    private IMlGoodsModelsService modelsService;
    @Autowired
    private IMlGoodsSpecificationsService specificationsService;
    @Autowired
    private WebMlGoodsDetailManage goodsDetailManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation("商品分页列表")
    @GetMapping("/getAllMallGoodsByCondition")
    public ApiRes getAllMallGoodsByCondition(@ModelAttribute AppGoodsVo goodsVo,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(goodsVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<MlGoods> page = goodsService.findAllMlByCondition(pageVo.initPage(),map);

        Page<AppGoodsVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() != null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<AppGoodsVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),AppGoodsVo.class);
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation("商城商品编辑")
    @GetMapping("/MallGoodsEdit")
    public ApiRes MallGoodsEdit(Long mlGoodsId){
        Map<String,Object> map = Maps.newHashMap();
        if(mlGoodsId != null){
            map.put("mlGoodsId",mlGoodsId);

            MlGoods goods = goodsService.selectById(mlGoodsId);
            map.put("goodDetail",goods);

            MlGoodsCategory category = categoryService.selectById(goods.getCategoryId());
            map.put("parentCategory",category.getPid());

            Map<String,Object> searchMap1 = Maps.newHashMap();
            map.put("goodsId",mlGoodsId);
            map.put("type",1);
            List<MlGoodsPics> banners = goodsPicsService.findAllByConditions(searchMap1);
            map.put("banners",banners);

            Map<String,Object> searchMap2 = Maps.newHashMap();
            map.put("goodsId",mlGoodsId);
            map.put("type",2);
            List<MlGoodsPics> detailPic = goodsPicsService.findAllByConditions(searchMap2);
            map.put("detailPic",detailPic);


            List<MlGoodsModels> models = modelsService.findAllByGoodsIdAndStatus(mlGoodsId,1);
            map.put("models",models);

            List<MlGoodsSpecifications> specifications = specificationsService.findAllByGoodsIdAndStatus(mlGoodsId,1);
            map.put("specifications",specifications);

        }else {
            List<MlGoodsCategory> categories = categoryService.findAllParent();
            map.put("parents",categories);
        }
        return ApiRes.Custom().success().addData(map);
    }

    @ApiOperation("删除现有的型号")
    @DeleteMapping("/modelInGoodDelete")
    public ApiRes modelInGoodDelete(Long goodId, Long modelId){
        return goodsDetailManage.modelInGoodDelete(goodId,modelId);
    }

    @ApiOperation("删除现有的规格")
    @DeleteMapping("/specInGoodDelete")
    public ApiRes specInGoodDelete(Long goodId,Long specId){
        return goodsDetailManage.specInGoodDelete(goodId,specId);
    }

    @ApiOperation("添加新的型号")
    @PostMapping("/modelInGoodAdd")
    public ApiRes modelInGoodAdd(Long goodId, String selectName, String title){
        return goodsDetailManage.modelInGoodAdd(goodId,selectName,title);
    }

    @ApiOperation("添加新的规格")
    @PostMapping("/specInGoodAdd")
    public ApiRes specInGoodAdd(Long goodId, String selectName, String title){
        return goodsDetailManage.specInGoodAdd(goodId,selectName,title);
    }

    @ApiOperation("添加商品")
    @PostMapping("/MallGoodsAdd")
    public ApiRes MallGoodsAdd(@ModelAttribute AppGoodsVo goodsVo,
                                  List<String> banners,
                                  List<String> detailPics,
                                  String modelTitle,
                                  List<String> modelsName,
                                  String specTitle,
                                  List<String> specsName){
        return goodsDetailManage.MallGoodsAdd(goodsVo,banners,detailPics,modelTitle,modelsName,specTitle,specsName);
    }

    @ApiOperation("添加商品更新")
    @PostMapping("/MallGoodsUpdate")
    public ApiRes MallGoodsUpdate(@ModelAttribute AppGoodsVo goodsVo,
                                  List<String> banners,
                                  List<String> detailPics){
        return goodsDetailManage.MallGoodsUpdate(goodsVo,banners,detailPics);
    }
}
