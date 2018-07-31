package cn.wind.xboot.controller.app.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ml.entity.MlGoods;
import cn.wind.db.ml.entity.MlGoodsCategory;
import cn.wind.db.ml.service.IMlGoodsCategoryService;
import cn.wind.db.ml.service.IMlGoodsService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 09:53
 * @Description:
 */
@Api(value = "商品展示管理",tags = "商品展示管理")
@RestController
@RequestMapping("/api/app/mallShow")
public class ApiGoodsShowController extends AppBaseController{

    @Autowired
    private IMlGoodsService mlGoodsService;
    @Autowired
    private IMlGoodsCategoryService mlGoodsCategoryService;

    @ApiOperation(value = "商品 首页 分页")
    @ApiImplicitParam(name = "type",value = "类型 1-新手必备 2-精选商品 3-热销榜 4-最新上架",dataType = "Integer",required = true,paramType = "query")
    @GetMapping("/pageInGoods")
    public ApiRes pageInGoods(Integer type, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            if(type==1){//新手必备
                map.put("necessary",1);
                map.put("order","sales DESC");
            }else if(type==2){//精选商品
                map.put("careselect",1);
                map.put("order","sales DESC");
            }else if(type==3){
                map.put("order","sales DESC");
            }else {
                map.put("order","create_time DESC");
            }
            map.put("status",1);
            Page<MlGoods> page = mlGoodsService.findAllByConditions(pageVo.initPage(),map);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "所有的父级类别")
    @GetMapping("/AllMasterCategory")
    public ApiRes AllMasterCategory(){
        try{
            EntityWrapper<MlGoodsCategory> ew=new EntityWrapper<>();
            ew.eq("pid",0);
            List<MlGoodsCategory> categories = mlGoodsCategoryService.selectList(ew);

            return ApiRes.Custom().addData(categories);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取该父级类别下的子级类别")
    @ApiImplicitParam(name = "masterId",value = "父级ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/AllCategoryForMaster")
    public ApiRes AllCategoryForMaster(Long masterId,@ModelAttribute PageVo pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("create_time,desc");
            pageVo.setSort(sort);
            EntityWrapper<MlGoodsCategory> ew=new EntityWrapper<MlGoodsCategory>();
            if(masterId != null){
                ew.eq("pid",masterId);
            }
            Page<MlGoodsCategory> page = mlGoodsCategoryService.selectPage(pageVo.initPage(),ew);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取该类别下的商品")
    @ApiImplicitParam(name = "categoryId",value = "类别ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInGoodsForCategory")
    public ApiRes pageInGoodsForCategory(Long categoryId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("categoryId",categoryId);
            map.put("status",1);
            map.put("order","sales DESC");

            Page<MlGoods> page = mlGoodsService.findAllByConditions(pageVo.initPage(),map);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }


}
