package cn.wind.xboot.service.app;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.DateUtil;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrLevelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 09:09
 * @Description:用户签到
 */
@Service
public class CXArUserSignManage {

    @Autowired
    private IArUserSignRecordService signRecordService;
    @Autowired
    private IArUserSignNumService signNumService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private ISrLevelsService levelsService;
    @Autowired
    private IArUserLevelRecordService levelRecordService;
    @Autowired
    private IArUserDailyRecordService dailyRecordService;

    /**
     * 签到
     * @param userId
     */
    @Transactional
    public ApiRes signIn(Long userId)throws Exception {
        Map<String,Object> map = new HashMap<>();
        int signDays = 0;
        ArUser user = userService.selectById(userId);
        if(user==null){
            throw new Exception("该用户不存在");
        }
        //获取当天签到记录
        LocalDate nowDate = LocalDate.now();
        ArUserSignRecord signRecordNow = signRecordService.findAllByDateAndUserId(userId,nowDate);
        if(signRecordNow==null){
            signRecordNow = new ArUserSignRecord();
            signRecordNow.setUserId(userId);
            signRecordNow.setSignDate(nowDate);
            signRecordService.insert(signRecordNow);
        }else {
            return ApiRes.Custom().failure(ApiStatus.ALREADY_SIGN_IN);
        }
        //获取当天的前一天
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String specifiedDay = sdf.format(date);
//        Date dateBefore = DateUtil.getSpecifiedDayBefore(specifiedDay);
        LocalDate dateBefore = nowDate.minusDays(1);
        ArUserSignRecord signRecord = signRecordService.findAllByDateAndUserId(userId,dateBefore);
        ArUserSignNum signNum = signNumService.findOneByUserId(userId);
        if(signRecord==null){//第一次签到
            if(signNum==null){
                signNum = new ArUserSignNum();
                signNum.setUserId(userId);
                signNum.setContinueDays(1);
                signNumService.insert(signNum);
            }else {
                signNum.setContinueDays(1);
                signNumService.updateById(signNum);
            }
            //用户活跃值
            user.setActiveNum(user.getActiveNum()+1L);
            signDays=0;
        }else {
            //连续7天签到
            if(signNum.getContinueDays()==6){
                signDays=7;
                signNum.setContinueDays(0);
                //用户活跃值
                user.setActiveNum(user.getActiveNum()+3L);
            }else {
                signDays=signNum.getContinueDays();
                signNum.setContinueDays(signNum.getContinueDays()+1);
                //用户活跃值
                user.setActiveNum(user.getActiveNum()+1L);
            }
            signNumService.updateById(signNum);
        }
        // TODO: 比较level,得出用户当前所在的level,以及后续业务处理
        SrLevels nextLevels = levelsService.findOneByLevel(user.getPerLevel()+1);
        if(nextLevels!=null){
            Long allNum = user.getActiveNum()+user.getCharmNum()+user.getWealthNum();
            if(allNum>=nextLevels.getScore()){
                user.setPerLevel(user.getPerLevel()+1);
            }
        }

        userService.updateById(user);

        //用户level新增记录数据表
        ArUserLevelRecord levelRecord = new ArUserLevelRecord();
        levelRecord.setUserId(userId);
        if(signDays==7){
            levelRecord.setNum(3);
        }else {
            levelRecord.setNum(1);
        }
        levelRecord.setType(1);
        levelRecord.setCategory(1);
        levelRecordService.insert(levelRecord);

        //用户每日新增数据记录数据表
        LocalDate date = LocalDate.now();
        ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(userId,date);
        if(dailyRecord==null){
            dailyRecord = new ArUserDailyRecord();
            dailyRecord.setUserId(userId);
            if(signDays==7){
                dailyRecord.setDailyActiveNum(3L);
            }else {
                dailyRecord.setDailyActiveNum(1L);
            }
            dailyRecord.setNowDate(date);
        }else {
            if(signDays==7){
                dailyRecord.setDailyActiveNum(dailyRecord.getDailyActiveNum()+3L);
            }else {
                dailyRecord.setDailyActiveNum(dailyRecord.getDailyActiveNum()+1L);
            }
        }
        dailyRecordService.insertOrUpdate(dailyRecord);

        map.put("signDays",signDays);
        map.put("activeNum",user.getActiveNum());
        map.put("level",user.getPerLevel());
        return ApiRes.Custom().addData(map);
    }
}
