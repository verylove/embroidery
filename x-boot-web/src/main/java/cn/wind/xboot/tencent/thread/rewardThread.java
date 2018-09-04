package cn.wind.xboot.tencent.thread;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.impl.ArUserMoneyRecordServiceImpl;
import cn.wind.db.ar.service.impl.ArUserServiceImpl;
import cn.wind.db.bc.entity.BcExceptionRecord;
import cn.wind.db.bc.entity.BcPkTotal;
import cn.wind.db.bc.entity.BcPkWeekRecord;
import cn.wind.db.bc.service.impl.BcExceptionRecordServiceImpl;
import cn.wind.db.bc.service.impl.BcPkTotalServiceImpl;
import cn.wind.db.bc.service.impl.BcPkWeekRecordServiceImpl;
import cn.wind.xboot.tencent.utils.SpringContextUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/29 16:01
 * @Description:
 */
public class rewardThread {

    private ArUserServiceImpl userService;

    private ArUserMoneyRecordServiceImpl moneyRecordService;

    private BcExceptionRecordServiceImpl exceptionRecordService;

    private BcPkWeekRecordServiceImpl pkWeekRecordService;

    private BcPkTotalServiceImpl bcPkTotalService;

    private boolean completed = false;

    public synchronized void rewardThread(String userID, String roomID, int category,Long worth){
        System.out.println("线程任务");
        if(this.completed){
            return;
        }
        try{
            userService = (ArUserServiceImpl) SpringContextUtil.getBean("arUserServiceImpl");
            moneyRecordService = (ArUserMoneyRecordServiceImpl) SpringContextUtil.getBean("arUserMoneyRecordServiceImpl");
            exceptionRecordService = (BcExceptionRecordServiceImpl) SpringContextUtil.getBean("bcExceptionRecordServiceImpl");
            pkWeekRecordService = (BcPkWeekRecordServiceImpl) SpringContextUtil.getBean("bcPkWeekRecordServiceImpl");
            bcPkTotalService = (BcPkTotalServiceImpl) SpringContextUtil.getBean("bcPkTotalServiceImpl");

            ArUser bcUser = userService.selectById(Long.parseLong(roomID));//主播
            bcUser.setLiveEarnings(bcUser.getLiveEarnings()+worth);
            userService.updateById(bcUser);

            BigDecimal cost = BigDecimal.valueOf(worth);
            String orderNo = "H1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
            moneyRecord.setUserId(Long.parseLong(userID));
            moneyRecord.setAmount(cost);
            moneyRecord.setType(2);//消费
            moneyRecord.setModule(15);
            moneyRecord.setOrderNo(orderNo);
            moneyRecord.setStatus(2);
            moneyRecord.setWay(4);//刺币
            moneyRecordService.insert(moneyRecord);

            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
            calendar.setTimeInMillis(System.currentTimeMillis());//获得当前的时间戳
            int weekNo = calendar.get(Calendar.WEEK_OF_YEAR);
            int yearNo = calendar.get(Calendar.YEAR);

            BcExceptionRecord exceptionRecord = new BcExceptionRecord();
            exceptionRecord.setUserId(Long.parseLong(userID));
            exceptionRecord.setBcUserId(Long.parseLong(roomID));
            if(category==1){
                exceptionRecord.setBcType(3);
            }else {
                exceptionRecord.setBcType(4);
            }
            exceptionRecord.setBcAmount(BigDecimal.valueOf(worth));
            exceptionRecord.setWeekNo(weekNo);
            exceptionRecord.setYearNo(yearNo);
            exceptionRecordService.insert(exceptionRecord);

            Map<String,Object> map = new HashMap<>();
            map.put("bcUserId",Long.parseLong(roomID));
            map.put("weekNo",weekNo);
            map.put("yearNo",yearNo);
            map.put("category",category);
            BcPkWeekRecord weekRecord = pkWeekRecordService.findOneByConditions(map);
            weekRecord.setLiveEarnings(weekRecord.getLiveEarnings()+worth);
            pkWeekRecordService.insert(weekRecord);

            BcPkTotal total = bcPkTotalService.findOneByUserId(Long.parseLong(roomID));
            total.setLiveEarnings(bcUser.getLiveEarnings());
            bcPkTotalService.updateById(total);

        }catch (Throwable t){
            ArUser user = userService.selectById(Long.parseLong(userID));
            user.setBalance(user.getBalance().add(BigDecimal.valueOf(worth)));
            userService.updateById(user);
        }
        this.completed = true;
    }

    public boolean isCompleted(){
        return this.completed;
    }
}
