package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserDailyRecord;
import cn.wind.db.ar.entity.ArUserLevelRecord;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.IArUserDailyRecordService;
import cn.wind.db.ar.service.IArUserLevelRecordService;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ml.entity.MlUserGoodsLogistics;
import cn.wind.db.ml.entity.MlUserGoodsSelect;
import cn.wind.db.ml.service.IMlUserGoodsLogisticsService;
import cn.wind.db.ml.service.IMlUserGoodsSelectService;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrLevelsService;
import cn.wind.xboot.enums.contants;
import cn.wind.xboot.mall.StockService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 11:20
 * @Description:
 */
@Service
public class CXArUserMoneyManage {

    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IMlUserGoodsLogisticsService logisticsService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserDailyRecordService dailyRecordService;
    @Autowired
    private IArUserLevelRecordService levelRecordService;
    @Autowired
    private ISrLevelsService levelsService;
    @Autowired
    private IMlUserGoodsSelectService goodsSelectService;
    @Autowired
    private CXMlGoodsManage goodsManage;

    @Transactional
    public boolean updateOrder(String payNo) {
        try{
            //1.修改订单状态
            Map<String,Object> map = Maps.newHashMap();
            map.put("orderNo",payNo);
            ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);
            moneyRecord.setStatus(2);
            moneyRecordService.updateById(moneyRecord);
            if(payNo.startsWith("A1")){//充值
                ArUser user = userService.selectById(moneyRecord.getUserId());
                user.setBalance(user.getBalance().add(moneyRecord.getAmount()));
                userService.updateById(user);

                //增加财富值
                LocalDate date = LocalDate.now();
                ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(moneyRecord.getUserId(),date);
                if(dailyRecord==null){
                    dailyRecord = new ArUserDailyRecord();
                    dailyRecord.setUserId(moneyRecord.getUserId());
                    dailyRecord.setDailyWealthNum(moneyRecord.getAmount().longValue());
                    dailyRecord.setNowDate(date);
                }else {
                    dailyRecord.setDailyWealthNum(dailyRecord.getDailyWealthNum()+moneyRecord.getAmount().longValue());
                }
                dailyRecordService.insertOrUpdate(dailyRecord);

                //level记录
                ArUserLevelRecord levelRecord = new ArUserLevelRecord();
                levelRecord.setUserId(moneyRecord.getUserId());
                levelRecord.setNum(contants.publishDyNum.intValue());
                levelRecord.setCategory(1);
                levelRecord.setType(4);
                levelRecordService.insert(levelRecord);

                //修改用户表level
                user.setWealthNum(user.getWealthNum()+moneyRecord.getAmount().longValue());
                EntityWrapper<SrLevels> ew = new EntityWrapper<>();
                List<String> columns = new ArrayList<>();
                columns.add("level");
                ew.orderAsc(columns);
                List<SrLevels> Levels = levelsService.selectList(ew);
                Long allNum = user.getActiveNum()+user.getCharmNum()+user.getWealthNum();
                long my = allNum.longValue();
                for(int i=0;i<Levels.size()-1;i++){
                    if(my>=Levels.get(i).getScore() && my<Levels.get(i+1).getScore()){
                        user.setPerLevel(Levels.get(i).getLevel());
                    }else if(my ==  Levels.get(i+1).getScore()){
                        user.setPerLevel(Levels.get(i+1).getLevel());
                    }
                }
                userService.updateById(user);
            }else {//商城
                //2.添加物流表信息
                MlUserGoodsLogistics logistics = new MlUserGoodsLogistics();
                logistics.setUserId(moneyRecord.getUserId());
                logistics.setOrderNo(moneyRecord.getOrderNo());
                logistics.setStatus(1);
                logisticsService.insert(logistics);
            }
        }catch (Exception e){
            e.printStackTrace();
            if(payNo.startsWith("A1")){//充值
                Map<String,Object> map = Maps.newHashMap();
                map.put("orderNo",payNo);
                ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);
                moneyRecord.setStatus(3);
                moneyRecordService.updateById(moneyRecord);
            }
            return false;
        }
        return true;
    }

    /**
     * 判断订单是否被处理过
     * @param payNo
     * @return
     */
    public boolean hasProcessed(String payNo) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("orderNo",payNo);
        ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);
        if(moneyRecord.getStatus() != 1){
            return true;
        }
        return false;
    }

    public void failOrder(String payNo) {
        //1.修改订单状态
        Map<String,Object> map = Maps.newHashMap();
        map.put("orderNo",payNo);
        ArUserMoneyRecord moneyRecord = moneyRecordService.findOnebyContions(map);
        moneyRecord.setStatus(3);
        moneyRecordService.updateById(moneyRecord);
    }

    @Transactional
    public void withdraw(BigDecimal amount, Long bankId, ArUser user)throws Exception {
        //1.添加提现记录
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(user.getId());
        moneyRecord.setAmount(amount);
        moneyRecord.setType(3);
        moneyRecord.setModule(1);
        String orderNo = "A2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(1);
        moneyRecord.setWay(4);
        moneyRecord.setBankId(bankId);
        moneyRecordService.insert(moneyRecord);

        //2.预扣用户余额
        user.setBalance(user.getBalance().subtract(amount));
        userService.updateById(user);
    }

    @Transactional
    public void cancelOrder(ArUserMoneyRecord moneyRecord)throws Exception {
        //1.检索订单商品
        Map<String,Object> map = Maps.newHashMap();
        map.put("userId",moneyRecord.getUserId());
        map.put("orderId",moneyRecord.getId());
        List<MlUserGoodsSelect> selects = goodsSelectService.findAllByConditons(map);

        List<Long> selectIds = selects.stream().map(MlUserGoodsSelect::getId).collect(Collectors.toList());

        //返回订单内的预扣库存
        for(MlUserGoodsSelect select:selects){
            // 商品ID
            String commodityId = select.getGoodsId()+"_"+select.getModelsId()+"_"+select.getSpecificationsId();
            // 库存ID
            String redisKey = "redis_key:stock:" + commodityId;
            goodsManage.addStock(redisKey,select.getNum(),commodityId);
        }

        //2.删除订单数据
        goodsSelectService.deleteBatchIds(selectIds);

        moneyRecordService.deleteById(moneyRecord.getId());
    }
}
