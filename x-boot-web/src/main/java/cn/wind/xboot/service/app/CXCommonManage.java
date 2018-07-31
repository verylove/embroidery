package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrLevelsService;
import cn.wind.xboot.enums.contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/20 11:17
 * @Description:
 */
@Service
public class CXCommonManage {

    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserGreatsService greatsService;
    @Autowired
    private IArUserDailyRecordService dailyRecordService;
    @Autowired
    private IArUserSentimentRecordService sentimentRecordService;
    @Autowired
    private IArUserLevelRecordService levelRecordService;
    @Autowired
    private ISrLevelsService levelsService;

    /**
     * 点赞操作共同处理
     * @param userId
     * @param greatId
     * @param type 1-其他被点赞 2-是圈子点赞 3-作品集被点赞
     * @throws Exception
     */
    @Transactional
    public void greatAction(Long userId,Long greatId,Integer type)throws Exception{

        //6.修改cx_ar_user_greats数据
        ArUserGreats userGreats = greatsService.findByUserIdAndGreatId(userId,greatId);
        if(userGreats==null){
            userGreats = new ArUserGreats();
            userGreats.setUserId(userId);
            userGreats.setGreatId(greatId);
        }else {
            userGreats.setNum(userGreats.getNum()+1);
        }
        greatsService.insertOrUpdate(userGreats);

        //7.点赞人当日数据
        LocalDate date =LocalDate.now();
        ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(userId,date);
        if(dailyRecord==null){
            dailyRecord = new ArUserDailyRecord();
            dailyRecord.setDailyGreatNum(1L);
            dailyRecord.setNowDate(date);
            dailyRecord.setUserId(userId);
        }else {
            dailyRecord.setDailyGreatNum(dailyRecord.getDailyGreatNum()+1);
        }
        dailyRecordService.insertOrUpdate(dailyRecord);

        //8.被点赞人当日数据
        ArUserDailyRecord dailyRecord2 = dailyRecordService.findOneByUserIdAndDate(greatId,date);
        if(dailyRecord2==null){
            dailyRecord2 = new ArUserDailyRecord();
            dailyRecord2.setDailyPraiseNum(1L);
            dailyRecord2.setNowDate(date);
            dailyRecord2.setUserId(greatId);
            dailyRecord2.setDailyCharmNum(contants.charmGreat.longValue());
            if(type==2){
                dailyRecord2.setDailyCircleGreat(1L);
                dailyRecord2.setScoreCricleGreat(contants.circleGreat);
                dailyRecord2.setDailySentiment(contants.circleGreatLong);
            }else if(type==3) {
                dailyRecord2.setDailyWorksGreat(1L);
                dailyRecord2.setScoreWorkGreat(contants.workGreat);
                dailyRecord2.setDailySentiment(contants.workGreatLong);
            }
        }else {
            dailyRecord2.setDailyPraiseNum(dailyRecord2.getDailyPraiseNum()+1);
            if(type==2){
                dailyRecord2.setDailyCircleGreat(dailyRecord2.getDailyCircleGreat()+1);
                dailyRecord2.setScoreCricleGreat(dailyRecord2.getScoreCricleGreat()+contants.circleGreat);
                dailyRecord2.setDailySentiment(dailyRecord2.getDailySentiment()+contants.circleGreatLong);
            }else if(type==3) {
                dailyRecord2.setDailyWorksGreat(dailyRecord2.getDailyWorksGreat()+1);
                dailyRecord2.setScoreWorkGreat(dailyRecord2.getScoreWorkGreat()+contants.workGreat);
                dailyRecord2.setDailySentiment(dailyRecord2.getDailySentiment()+contants.workGreatLong);
            }
            dailyRecord2.setDailyCharmNum(dailyRecord2.getDailyCharmNum()+contants.charmGreat);
        }
        dailyRecordService.insertOrUpdate(dailyRecord2);

        //9.修改被点赞人数据
        ArUser greatUser = userService.selectById(greatId);
        greatUser.setPraiseNum(greatUser.getPraiseNum()+1);
        if(type==2){
            greatUser.setSentimentNum(greatUser.getSentimentNum()+contants.circleGreatLong);
        }else if(type==3) {
            greatUser.setSentimentNum(greatUser.getSentimentNum()+contants.workGreatLong);
        }
        greatUser.setCharmNum(greatUser.getCharmNum()+contants.charmGreat);
        SrLevels nextLevels = levelsService.findOneByLevel(greatUser.getPerLevel()+1);
        if(nextLevels!=null){
            Long allNum = greatUser.getActiveNum()+greatUser.getCharmNum()+greatUser.getWealthNum();
            if(allNum>=nextLevels.getScore()){
                greatUser.setPerLevel(greatUser.getPerLevel()+1);
            }
        }
        userService.updateById(greatUser);

        //10.添加人气数据记录
        if(type==2){
            ArUserSentimentRecord sentimentRecord = new ArUserSentimentRecord();
            sentimentRecord.setUserId(greatId);
            sentimentRecord.setNum(contants.circleGreat);
            sentimentRecord.setType(4);
            sentimentRecordService.insert(sentimentRecord);
        }else if(type==3){
            ArUserSentimentRecord sentimentRecord = new ArUserSentimentRecord();
            sentimentRecord.setUserId(greatId);
            sentimentRecord.setNum(contants.circleGreat);
            sentimentRecord.setType(2);
            sentimentRecordService.insert(sentimentRecord);
        }

        //11.添加level数据记录
        ArUserLevelRecord levelRecord = new ArUserLevelRecord();
        levelRecord.setUserId(greatId);
        levelRecord.setNum(contants.charmGreat);
        levelRecord.setCategory(2);
        levelRecord.setType(6);
        levelRecordService.insert(levelRecord);
    }
}
