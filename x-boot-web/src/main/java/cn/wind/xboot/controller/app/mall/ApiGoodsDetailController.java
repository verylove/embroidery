package cn.wind.xboot.controller.app.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ml.entity.*;
import cn.wind.db.ml.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ml.mlGoodsSelectDto;
import cn.wind.xboot.mall.StockService;
import cn.wind.xboot.service.app.CXMlGoodsManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ml.MlGoodsShopCartVo;
import cn.wind.xboot.vo.app.ml.MlGoodsToSelectVo;
import cn.wind.xboot.vo.app.ml.MlGoodsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 11:41
 * @Description:
 */
@Api(value = "单个商品管理",tags = "单个商品管理")
@RestController
@RequestMapping("/api/app/goods")
public class ApiGoodsDetailController extends AppBaseController{

    @Autowired
    private IMlGoodsService goodsService;
    @Autowired
    private IMlGoodsPicsService goodsPicsService;
    @Autowired
    private IMlGoodsSpecificationsService specificationsService;
    @Autowired
    private IMlGoodsModelsService modelsService;
    @Autowired
    private IMlGoodsDetailService goodsDetailService;
    @Autowired
    private CXMlGoodsManage goodsManage;
    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "goodsId",value = "商品ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/goodsDetail")
    public ApiRes goodsDetail(Long goodsId){
        try{
            MlGoods goods = goodsService.selectById(goodsId);
            if(goods.getStatus()==0){
                return ApiRes.Custom().failure(ApiStatus.GOODS_ALREADY_SHELVES);
            }
            MlGoodsVo vo = new MlGoodsVo();
            BeanUtils.copyProperties(goods,vo);

            Map<String,Object> map = Maps.newHashMap();
            map.put("goodsId",goodsId);
            map.put("type",1);
            List<MlGoodsPics> figurePics = goodsPicsService.findAllByConditions(map);
            vo.setFigurePics(figurePics);

            map = Maps.newHashMap();
            map.put("goodsId",goodsId);
            map.put("type",2);
            List<MlGoodsPics> detailPics = goodsPicsService.findAllByConditions(map);
            vo.setDetailPics(detailPics);

            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "购买配置 选择")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId",value = "商品ID",required = true,paramType = "query"),
            @ApiImplicitParam(name = "specificationsId",value = "规格",required = false,paramType = "query"),
            @ApiImplicitParam(name = "modelsId",value = "型号",required = false,paramType = "query")
    })
    @GetMapping("/buyGoodsSelect")
    public ApiRes buyGoodsSelect(@RequestParam Long goodsId,@RequestParam(required = false)Long specificationsId, @RequestParam(required = false)Long modelsId){
        try{
            MlGoods goods = goodsService.selectById(goodsId);
            if(goods.getStatus()==0){
                return ApiRes.Custom().failure(ApiStatus.GOODS_ALREADY_SHELVES);
            }
            MlGoodsToSelectVo vo = new MlGoodsToSelectVo();
            vo.setId(goodsId);

            /**
             * 警告后来者，此处建的表的确很傻X,有更屌的方法请优化
             */
            List<MlGoodsSpecifications> specifications = specificationsService.findAllByGoodsIdAndStatus(goodsId,1);

            List<MlGoodsModels> models = modelsService.findAllByGoodsIdAndStatus(goodsId,1);

            if((specificationsId==null||specificationsId==0L)&&(modelsId==null||modelsId==0L)){
                vo.setCoverImg(goods.getCoverImg());
                vo.setSpecifications(specifications);
                vo.setModels(models);
                vo.setPrice(goods.getPrice());
                vo.setRemainNum(goods.getRemainNum());
            }
            if(specificationsId!=null&&specificationsId!=0L){
                if(models==null||models.size()<1){
                    Map map = Maps.newHashMap();
                    map.put("goodsId",goodsId);
                    map.put("modelsId",0);
                    map.put("specificationsId",specificationsId);
                    MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);

                    vo.setPrice(goodsDetail.getPrice().toString());
                    vo.setRemainNum(goodsDetail.getNum());
                }else {
                    Map map = Maps.newHashMap();
                    map.put("goodsId",goodsId);
                    map.put("specificationsId",specificationsId);
                    List<MlGoodsDetail> goodsDetails = goodsDetailService.findAllByConditions(map);
                    List<Long> modelsIds = goodsDetails.stream().map(MlGoodsDetail::getModelsId).collect(Collectors.toList());

                    for(MlGoodsModels mlGoodsModels:models){
                        if(!modelsIds.contains(mlGoodsModels.getId())){
                            mlGoodsModels.setStatus(0);
                        }
                    }
                    vo.setModels(models);
                }
            }
            if(modelsId!=null&&modelsId!=0L){
                if(specifications==null||specifications.size()<1){
                    Map map = Maps.newHashMap();
                    map.put("goodsId",goodsId);
                    map.put("modelsId",modelsId);
                    map.put("specificationsId",0);
                    MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);

                    vo.setPrice(goodsDetail.getPrice().toString());
                    vo.setRemainNum(goodsDetail.getNum());
                }else {
                    Map map = Maps.newHashMap();
                    map.put("goodsId",goodsId);
                    map.put("modelsId",modelsId);
                    List<MlGoodsDetail> goodsDetails = goodsDetailService.findAllByConditions(map);
                    List<Long> specificationsIds = goodsDetails.stream().map(MlGoodsDetail::getSpecificationsId).collect(Collectors.toList());

                    for(MlGoodsSpecifications specifications1:specifications){
                        if(!specificationsIds.contains(specifications1.getId())){
                            specifications1.setStatus(0);
                        }
                    }
                    vo.setSpecifications(specifications);
                }
            }
            if ((specificationsId!=null||specificationsId!=0L)&&(modelsId!=null||modelsId!=0L)){
                Map map = Maps.newHashMap();
                map.put("goodsId",goodsId);
                map.put("modelsId",modelsId);
                map.put("specificationsId",specificationsId);
                MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);

                vo.setPrice(goodsDetail.getPrice().toString());
                vo.setRemainNum(goodsDetail.getNum());
            }
            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "添加购物车")
    @PostMapping("/addShopCart")
    public ApiRes addShopCart(mlGoodsSelectDto dto){
        try{
            //判断该商品是否还有库存
            Map map = Maps.newHashMap();
            map.put("goodsId",dto.getGoodsId());
            map.put("modelsId",dto.getModelsId()==null?0:dto.getModelsId());
            map.put("specificationsId",dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
            MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);
            if(goodsDetail==null || goodsDetail.getNum()<dto.getNum()){
                return ApiRes.Custom().failure(ApiStatus.GOODS_NUM_NOTHING);
            }

            goodsManage.addShopCart(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "商品邮费")
    @ApiImplicitParam(name = "goodsId",value = "商品ID",required = true,paramType = "query")
    @GetMapping("/goodsPostage")
    public ApiRes goodsPostage(Long goodsId){
        try{
            MlGoods goods = goodsService.selectById(goodsId);
            if(goods.getStatus()==0){
                return ApiRes.Custom().failure(ApiStatus.GOODS_ALREADY_SHELVES);
            }
            return ApiRes.Custom().addData(goods.getPostage());
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "购物车 分页")
    @GetMapping("/pageInShopCart")
    public ApiRes pageInShopCart(@ModelAttribute PageVo<MlUserGoodsSelect> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("create_time,desc");
            pageVo.setSort(sort);
            EntityWrapper<MlUserGoodsSelect> ew=new EntityWrapper<MlUserGoodsSelect>();
            ew.eq("user_id",getUserId()).and().eq("type",1);

            Page<MlUserGoodsSelect> page = goodsSelectService.selectPage(pageVo.initPage(),ew);

            Page<MlGoodsShopCartVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            List<Long> specificationsIds = page.getRecords().stream().map(MlUserGoodsSelect::getSpecificationsId).collect(Collectors.toList());

            List<Long> modelsIds = page.getRecords().stream().map(MlUserGoodsSelect::getModelsId).collect(Collectors.toList());

            List<Long> goodsIds = page.getRecords().stream().map(MlUserGoodsSelect::getGoodsId).collect(Collectors.toList());

            List<MlGoodsSpecifications> specifications = specificationsService.selectBatchIds(specificationsIds);
            Map<Long,String> specificationsMap = specifications.stream().collect(Collectors.toMap(MlGoodsSpecifications::getId,MlGoodsSpecifications::getSelectName));

            List<MlGoodsModels> models = modelsService.selectBatchIds(modelsIds);
            Map<Long,String> modelsMap = models.stream().collect(Collectors.toMap(MlGoodsModels::getId,MlGoodsModels::getSelectName));

            List<MlGoods> goods = goodsService.selectBatchIds(goodsIds);
            Map<Long,MlGoods> goodsMap = goods.stream().collect(Collectors.toMap(MlGoods::getId, Function.identity()));

            List<MlGoodsShopCartVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),MlGoodsShopCartVo.class);
            for(MlGoodsShopCartVo vo:vos){
                vo.setCoverImg(goodsMap.get(vo.getGoodsId()).getCoverImg());
                vo.setModelsSelectName(modelsMap.get(vo.getModelsId()));
                vo.setName(goodsMap.get(vo.getGoodsId()).getName());
                vo.setSpecificationsSelectName(specificationsMap.get(vo.getSpecificationsId()));
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "购物车 删除")
    @DeleteMapping("/deleteShopCart")
    public ApiRes deleteShopCart(List<Long> goodsSelectIds){
        try{
            goodsManage.deleteShopCart(goodsSelectIds);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "生成订单 结算")
    @ApiImplicitParam(name = "userAddress",value = "收货地址",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/settlement")
    public ApiRes settlement(List<mlGoodsSelectDto> dtos,Long userAddress){
        try{
            return goodsManage.settlement(dtos,userAddress,getUserId());
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }



//    @RequestMapping(value = "getStock", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Object getStock() {
//        // 商品ID
//        long commodityId = 1;
//        // 库存ID
//        String redisKey = "redis_key:stock:" + commodityId;
//
//        return stockService.getStock(redisKey);
//    }
//
//    @RequestMapping(value = "addStock", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Object addStock() {
//        // 商品ID
//        long commodityId = 2;
//        // 库存ID
//        String redisKey = "redis_key:stock:" + commodityId;
//
//        return stockService.addStock(redisKey, 2);
//    }


}
