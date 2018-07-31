package cn.wind.xboot.service.app;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import cn.wind.db.ml.entity.MlGoodsDetail;
import cn.wind.db.ml.entity.MlUserAddress;
import cn.wind.db.ml.entity.MlUserGoodsSelect;
import cn.wind.db.ml.service.IMlGoodsDetailService;
import cn.wind.db.ml.service.IMlGoodsService;
import cn.wind.db.ml.service.IMlUserAddressService;
import cn.wind.db.ml.service.IMlUserGoodsSelectService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.dto.app.ml.mlGoodsSelectDto;
import cn.wind.xboot.mall.StockService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 18:29
 * @Description:
 */
@Service
public class CXMlGoodsManage {

    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IMlGoodsService goodsService;
    @Autowired
    private StockService stockService;
    @Autowired
    private IMlGoodsDetailService goodsDetailService;
    @Autowired
    private IMlUserAddressService addressService;
    @Autowired
    private ISrAreaService areaService;

    @Transactional
    public void addShopCart(mlGoodsSelectDto dto, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("goodsId",dto.getGoodsId());
        map.put("specificationsId",dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
        map.put("modelsId",dto.getModelsId()==null?0:dto.getModelsId());
        map.put("type",1);
        MlUserGoodsSelect goodsSelect = goodsSelectService.findOneByConditions(map);

        if(goodsSelect!=null){
            if(goodsSelect.getStatus()==1){
                goodsSelect.setPrice(goodsSelect.getPrice().add(dto.getPrice()));
                goodsSelect.setNum(goodsSelect.getNum()+dto.getNum());
            }else {
                goodsSelect.setPrice(dto.getPrice());
                goodsSelect.setNum(dto.getNum());
                goodsSelect.setStatus(1);
            }
            goodsSelectService.updateById(goodsSelect);
        }else {
            //1.添加cx_ml_user_goods_select数据
            goodsSelect = new MlUserGoodsSelect();
            BeanUtils.copyProperties(dto,goodsSelect);
            goodsSelect.setUserId(userId);
            goodsSelect.setType(1);
            goodsSelect.setStatus(1);
            goodsSelectService.insert(goodsSelect);
        }

    }


    /**
     * 生成订单 修改库存
     * @param dtos
     * @param userAddress
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    public ApiRes settlement(List<mlGoodsSelectDto> dtos, Long userAddress, Long userId)throws Exception {
        //1.预扣库存
        for(mlGoodsSelectDto dto:dtos){
            // 商品ID
            String commodityId = dto.getGoodsId()+"_"+(dto.getModelsId()==null?0:dto.getModelsId())+"_"+(dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
            // 库存ID
            String redisKey = "redis_key:stock:" + commodityId;
            long stock = stockService.stock(redisKey, 7L, dto.getNum(), () -> initStock(commodityId));
            if(stock==-2){
                goodsFailure(dto,userId);
                return ApiRes.Custom().failure(ApiStatus.GOODS_NUM_NOTHING);
            }
        }

        //1.添加订单
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);

        BigDecimal amount = new BigDecimal("0.00");
        for(mlGoodsSelectDto dto:dtos){
            amount = amount.add(dto.getPrice());
        }
        List<Long> goodsId = dtos.stream().map(mlGoodsSelectDto::getGoodsId).collect(Collectors.toList());
        BigDecimal postage = goodsService.findPostageByIds(goodsId);
        moneyRecord.setPostage(postage);

        moneyRecord.setAmount(amount.add(postage));
        moneyRecord.setType(2);
        moneyRecord.setModule(14);
        String orderNo = "H1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(1);
        MlUserAddress address = addressService.selectById(userAddress);
        moneyRecord.setReceiptName(address.getName());
        moneyRecord.setReceiptPhone(address.getPhone());

        List<Long> areaId = Lists.newArrayList();
        areaId.add(address.getProvince());
        areaId.add(address.getCity());
        areaId.add(address.getCounty());
        List<SrArea> areas = areaService.selectBatchIds(areaId);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId,SrArea::getName));
        StringBuilder builder = new StringBuilder();
        builder.append(areaMap.get(address.getProvince()));
        builder.append(areaMap.get(address.getCity()));
        builder.append(areaMap.get(address.getCounty()));
        builder.append(address.getAdressDetail());

        moneyRecord.setReceiptAddress(builder.toString());

        moneyRecordService.insert(moneyRecord);

        //2.更新插入cx_ml_user_goods_select数据
        int i = 0;
        for(mlGoodsSelectDto dto:dtos){
            //更新插入cx_ml_user_goods_select数据
            Map map = Maps.newHashMap();
            map.put("userId",userId);
            map.put("goodsId",dto.getGoodsId());
            map.put("specificationsId",dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
            map.put("modelsId",dto.getModelsId()==null?0:dto.getModelsId());
            map.put("type",1);
            MlUserGoodsSelect goodsSelect = goodsSelectService.findOneByConditions(map);
            if(goodsSelect != null){
                goodsSelect.setPrice(dto.getPrice());
                goodsSelect.setNum(dto.getNum());
                goodsSelect.setType(2);
                goodsSelect.setOrderId(moneyRecord.getId());
                goodsSelectService.updateById(goodsSelect);
            }else {
                goodsSelect = new MlUserGoodsSelect();
                goodsSelect.setUserId(userId);
                goodsSelect.setGoodsId(dto.getGoodsId());
                goodsSelect.setSpecificationsId(dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
                goodsSelect.setModelsId(dto.getModelsId()==null?0:dto.getModelsId());
                goodsSelect.setType(2);
                goodsSelect.setPrice(dto.getPrice());
                goodsSelect.setNum(dto.getNum());
                goodsSelect.setOrderId(moneyRecord.getId());
                goodsSelectService.insert(goodsSelect);
            }
        }



        return ApiRes.Custom().addData(orderNo);
    }

    /**
     * 获取初始的库存
     *
     * @return
     */
    private long initStock(String commodityId) {
        // TODO 这里做一些初始化库存的操作
        String[] commodityIds = commodityId.split("_");
        Map map = Maps.newHashMap();
        map.put("goodsId",Long.parseLong(commodityIds[0]));
        map.put("modelsId",Long.parseLong(commodityIds[1]));
        map.put("specificationsId",Long.parseLong(commodityIds[2]));
        MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);
        if(goodsDetail==null){
            return 0;
        }
        return goodsDetail.getNum();
    }

    //如果该物品存在购物车，修改为商品失效
    public void goodsFailure(mlGoodsSelectDto dto, Long userId) {
        Map map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("goodsId",dto.getGoodsId());
        map.put("specificationsId",dto.getSpecificationsId()==null?0:dto.getSpecificationsId());
        map.put("modelsId",dto.getModelsId()==null?0:dto.getModelsId());
        map.put("type",1);
        MlUserGoodsSelect goodsSelect = goodsSelectService.findOneByConditions(map);
        if(goodsSelect!=null){
            goodsSelect.setStatus(0);
            goodsSelectService.updateById(goodsSelect);
        }
    }

    /**
     * 加库存(还原库存)
     *
     * @param key    库存key
     * @param num    库存数量
     * @return
     */
    public long addStock(String key, int num,String commodityId) {

        return stockService.addStock(key, 7L, num,() -> initStock(commodityId));
    }

    @Transactional
    public void stockInMySql(List<MlUserGoodsSelect> goodsSelectList)throws Exception{
        for(MlUserGoodsSelect goodsSelect:goodsSelectList){
            Map map = Maps.newHashMap();
            map.put("goodsId",goodsSelect.getGoodsId());
            map.put("modelsId",goodsSelect.getModelsId());
            map.put("specificationsId",goodsSelect.getSpecificationsId());
            MlGoodsDetail goodsDetail = goodsDetailService.findOneByConditions(map);
            if(goodsDetail==null){
                throw new Exception();
            }
            if(goodsDetail.getNum()==-1){//不限量
                continue;
            }
            map = Maps.newHashMap();
            map.put("goodDetailId",goodsDetail.getId());
            map.put("num",goodsSelect.getNum());
            map.put("version",goodsDetail.getVersion());
            Integer row = goodsDetailService.updateByConditions(map);
            if(row==0){
                throw new Exception();
            }
        }
    }

    @Transactional
    public void deleteShopCart(List<Long> goodsSelectIds)throws Exception {
        goodsSelectService.deleteBatchIds(goodsSelectIds);
    }
}
