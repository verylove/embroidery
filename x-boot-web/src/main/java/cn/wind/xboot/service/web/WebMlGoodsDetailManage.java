package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ml.entity.*;
import cn.wind.db.ml.service.*;
import cn.wind.xboot.mall.StockService;
import cn.wind.xboot.vo.web.ml.goods.AppGoodsVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/10 10:48
 * @Description:
 */
@Service
public class WebMlGoodsDetailManage {

    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;
    @Autowired
    private StockService stockService;
    @Autowired
    private IMlGoodsDetailService detailService;
    @Autowired
    private IMlGoodsModelsService modelsService;
    @Autowired
    private IMlGoodsSpecificationsService specificationsService;
    @Autowired
    private IMlGoodsService goodsService;
    @Autowired
    private IMlGoodsPicsService picsService;

    @Transactional
    public ApiRes modelInGoodDelete(Long goodId, Long modelId) {
        //1.判断该型号是否被加入购物车或购买
        Map<String,Object> map = Maps.newHashMap();
        map.put("goodId",goodId);
        map.put("modelId",modelId);
        List<MlUserGoodsSelect> selectList = goodsSelectService.findAllByModels(map);
        if(selectList != null || selectList.size()>0){
            return ApiRes.Custom().failure("该型号已被订购，无法删除");
        }

        //2.删除缓存
        map = Maps.newHashMap();
        map.put("goodsId",goodId);
        map.put("modelsId",modelId);
        List<MlGoodsDetail> goodsDetails = detailService.findAllByConditions(map);
        for(MlGoodsDetail goodsDetail:goodsDetails){
            String commodityId = goodId+"_"+(modelId==null?0:modelId)+"_"+(goodsDetail.getSpecificationsId()==null?0:goodsDetail.getSpecificationsId());
            stockService.deleteStock(commodityId);

            detailService.deleteById(goodsDetail.getId());
        }

        //3.删除该model
        MlGoodsModels goodsModels = modelsService.selectById(modelId);
        if(goodsModels != null){
            goodsModels.setStatus(0);
            modelsService.updateById(goodsModels);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes specInGoodDelete(Long goodId, Long specId) {
        //1.判断该型号是否被加入购物车或购买
        Map<String,Object> map = Maps.newHashMap();
        map.put("goodId",goodId);
        map.put("specificationsId",specId);
        List<MlUserGoodsSelect> selectList = goodsSelectService.findAllBySpecs(map);
        if(selectList != null || selectList.size()>0){
            return ApiRes.Custom().failure("该型号已被订购，无法删除");
        }

        //2.删除缓存
        map = Maps.newHashMap();
        map.put("goodsId",goodId);
        map.put("specificationsId",specId);
        List<MlGoodsDetail> goodsDetails = detailService.findAllByConditions(map);
        for(MlGoodsDetail goodsDetail:goodsDetails){
            String commodityId = goodId+"_"+(goodsDetail.getModelsId()==null?0:goodsDetail.getModelsId())+"_"+(specId==null?0:specId);
            stockService.deleteStock(commodityId);

            detailService.deleteById(goodsDetail.getId());
        }

        //3.删除该model
        MlGoodsSpecifications specifications = specificationsService.selectById(specId);
        if(specifications != null){
            specifications.setStatus(0);
            specificationsService.updateById(specifications);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes modelInGoodAdd(Long goodId, String selectName, String title) {
        MlGoodsModels mlGoodsModels = new MlGoodsModels();
        mlGoodsModels.setTitle(title);
        mlGoodsModels.setGoodsId(goodId);
        mlGoodsModels.setSelectName(selectName);
        mlGoodsModels.setStatus(1);
        modelsService.insert(mlGoodsModels);
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes specInGoodAdd(Long goodId, String selectName, String title) {
        MlGoodsSpecifications specifications = new MlGoodsSpecifications();
        specifications.setTitle(title);
        specifications.setGoodsId(goodId);
        specifications.setSelectName(selectName);
        specifications.setStatus(1);
        specificationsService.insert(specifications);
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes MallGoodsAdd(AppGoodsVo goodsVo, List<String> banners, List<String> detailPics, String modelTitle, List<String> modelsName, String specTitle, List<String> specsName) {
        MlGoods goods = new MlGoods();
        BeanUtils.copyProperties(goodsVo,goods);
        goods.setRemainNum(0L);
        goods.setSales(0L);
        goods.setStatus(1);
        goodsService.insert(goods);

        List<MlGoodsPics> pics = Lists.newArrayList();
        for(String banner: banners){
            MlGoodsPics pic = new MlGoodsPics();
            pic.setImg(banner);
            pic.setGoodsId(goods.getId());
            pic.setType(1);
            pics.add(pic);
        }

        for(String detailPic:detailPics){
            MlGoodsPics pic = new MlGoodsPics();
            pic.setImg(detailPic);
            pic.setGoodsId(goods.getId());
            pic.setType(2);
            pics.add(pic);
        }
        picsService.insertBatch(pics);

        List<MlGoodsModels> models = Lists.newArrayList();
        for(String model:modelsName){
            MlGoodsModels mlGoodsModels = new MlGoodsModels();
            mlGoodsModels.setTitle(modelTitle);
            mlGoodsModels.setGoodsId(goods.getId());
            mlGoodsModels.setSelectName(model);
            mlGoodsModels.setStatus(1);
            models.add(mlGoodsModels);
        }
        modelsService.insertBatch(models);

        List<MlGoodsSpecifications> specifications = Lists.newArrayList();
        for(String spec:specsName){
            MlGoodsSpecifications specification = new MlGoodsSpecifications();
            specification.setTitle(specTitle);
            specification.setGoodsId(goods.getId());
            specification.setSelectName(spec);
            specification.setStatus(1);
            specifications.add(specification);
        }
        specificationsService.insertBatch(specifications);
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes MallGoodsUpdate(AppGoodsVo goodsVo, List<String> banners, List<String> detailPics) {
        MlGoods goods = goodsService.selectById(goodsVo.getId());
        if(goods == null){
            return ApiRes.Custom().failure("该商品不存在");
        }
        BeanUtils.copyProperties(goodsVo,goods);
        goodsService.updateById(goods);

        Map<String,Object> map = Maps.newHashMap();
        map.put("goodsId",goodsVo.getId());
        List<MlGoodsPics> pics = picsService.findAllByConditions(map);
        if(pics != null){
            picsService.deleteBatchIds(pics.stream().map(MlGoodsPics::getId).collect(Collectors.toList()));
        }

        pics = Lists.newArrayList();
        for(String banner: banners){
            MlGoodsPics pic = new MlGoodsPics();
            pic.setImg(banner);
            pic.setGoodsId(goods.getId());
            pic.setType(1);
            pics.add(pic);
        }

        for(String detailPic:detailPics){
            MlGoodsPics pic = new MlGoodsPics();
            pic.setImg(detailPic);
            pic.setGoodsId(goods.getId());
            pic.setType(2);
            pics.add(pic);
        }
        picsService.insertBatch(pics);
        return ApiRes.Custom().success();
    }
}
