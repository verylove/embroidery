package cn.wind.xboot.controller.app.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import cn.wind.db.ml.entity.*;
import cn.wind.db.ml.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.app.CXArUserMoneyManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ml.MlGoodsOrderVo;
import cn.wind.xboot.vo.app.ml.MlGoodsShopCartVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/31 10:11
 * @Description:
 */
@Api(value = "商城订单管理",tags = "商城订单管理")
@RestController
@RequestMapping("/api/app/order")
public class ApiGoodsOrderController extends AppBaseController{

    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;
    @Autowired
    private IMlGoodsSpecificationsService specificationsService;
    @Autowired
    private IMlGoodsModelsService modelsService;
    @Autowired
    private IMlGoodsService goodsService;
    @Autowired
    private IMlUserGoodsLogisticsService goodsLogisticsService;
    @Autowired
    private CXArUserMoneyManage moneyManage;

    @ApiOperation(value = "订单")
    @ApiImplicitParam(name = "type",value = "类型 1-全部 2-待支付 3-待收货",dataType = "Integer",required = true,paramType = "query")
    @GetMapping("/orders")
    public ApiRes allOrder(Integer type, @ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("create_time,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            List<Integer> status = Lists.newArrayList();
            if(type==1){
                status.add(1);
                status.add(2);
            }else if(type==2){
                status.add(1);
            }else {
                status.add(2);
            }
            ew.eq("user_id",getUserId()).and().eq("module",14).and().in("status",status);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);

            Page<MlGoodsOrderVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            List<MlGoodsOrderVo> vos = Lists.newArrayList();
            for(ArUserMoneyRecord record:page.getRecords()){
                MlGoodsOrderVo vo = new MlGoodsOrderVo();
                vo.setId(record.getId());
                vo.setStatus(record.getStatus());
                vo.setOrderNo(record.getOrderNo());
                vo.setCreateTime(record.getCreateTime());

                Map<String,Object> map = Maps.newHashMap();
                map.put("userId",record.getUserId());
                map.put("orderId",record.getId());
                List<MlUserGoodsSelect> selects = goodsSelectService.findAllByConditons(map);

                List<Long> specificationsIds = selects.stream().map(MlUserGoodsSelect::getSpecificationsId).collect(Collectors.toList());

                List<Long> modelsIds = selects.stream().map(MlUserGoodsSelect::getModelsId).collect(Collectors.toList());

                List<Long> goodsIds = selects.stream().map(MlUserGoodsSelect::getGoodsId).collect(Collectors.toList());

                List<MlGoodsSpecifications> specifications = specificationsService.selectBatchIds(specificationsIds);
                Map<Long,String> specificationsMap = specifications.stream().collect(Collectors.toMap(MlGoodsSpecifications::getId,MlGoodsSpecifications::getSelectName));

                List<MlGoodsModels> models = modelsService.selectBatchIds(modelsIds);
                Map<Long,String> modelsMap = models.stream().collect(Collectors.toMap(MlGoodsModels::getId,MlGoodsModels::getSelectName));

                List<MlGoods> goods = goodsService.selectBatchIds(goodsIds);
                Map<Long,MlGoods> goodsMap = goods.stream().collect(Collectors.toMap(MlGoods::getId, Function.identity()));

                List<MlGoodsShopCartVo> cartVos = ObjectMapperUtils.mapAll(page.getRecords(),MlGoodsShopCartVo.class);
                for(MlGoodsShopCartVo vartVo:cartVos){
                    vartVo.setCoverImg(goodsMap.get(vartVo.getGoodsId()).getCoverImg());
                    vartVo.setModelsSelectName(modelsMap.get(vartVo.getModelsId()));
                    vartVo.setName(goodsMap.get(vartVo.getGoodsId()).getName());
                    vartVo.setSpecificationsSelectName(specificationsMap.get(vartVo.getSpecificationsId()));
                }
                vo.setVos(cartVos);
                vos.add(vo);
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "orderId",value = "订单ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/cancelOrder")
    public ApiRes cancelOrder(Long orderId){
        try{
            ArUserMoneyRecord moneyRecord = moneyRecordService.selectById(orderId);
            if(moneyRecord==null){
                return ApiRes.Custom().failure(ApiStatus.ORDER_NOT_EXIST);
            }
            moneyManage.cancelOrder(moneyRecord);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "查看物流")
    @ApiImplicitParam(name = "orderNo",value = "订单编号",dataType = "String",required = true,paramType = "query")
    @GetMapping("/logistics")
    public ApiRes logistics(String orderNo){
        try{
            List<Integer> status = Lists.newArrayList();
            status.add(1);
            status.add(2);
            status.add(3);
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("orderNo",orderNo);
            map.put("statusList",status);
            MlUserGoodsLogistics goodsLogistics = goodsLogisticsService.findOneByConditions(map);
            if(goodsLogistics==null){
                return ApiRes.Custom().failure(ApiStatus.LOGISTICS_NOTHING);
            }else if(goodsLogistics.getStatus()==1){
                return ApiRes.Custom().failure(ApiStatus.LOGISTICS_NO_DELIVERY);
            }else if (goodsLogistics.getStatus()==3){
                return ApiRes.Custom().failure(ApiStatus.LOGISTICS_SIGN_RECEIVING);
            }else {
                return ApiRes.Custom().addData(goodsLogistics);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
