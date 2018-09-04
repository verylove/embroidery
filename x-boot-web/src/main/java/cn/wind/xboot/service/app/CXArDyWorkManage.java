package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.db.sr.service.ISrLevelsService;
import cn.wind.xboot.dto.app.ar.arUserDyWorkDto;
import cn.wind.xboot.enums.contants;
import cn.wind.xboot.tencent.thread.MyEvaluateThread;
import cn.wind.xboot.tencent.thread.MyGreatThread;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 18:42
 * @Description:
 */
@Service
public class CXArDyWorkManage {

    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserDyWorksService dyWorksService;
    @Autowired
    private IArUserDyPicService dyPicService;
    @Autowired
    private IArUserDyGreatNumService dyGreatNumService;
    @Autowired
    private IArUserDyGreatRecordService dyGreatRecordService;
    @Autowired
    private IArUserDyEvaluatesService dyEvaluatesService;
    @Autowired
    private IArUserDyEvaluatesGreatNumService dyEvaluatesGreatNumService;
    @Autowired
    private IArUserDyEvaluatesGreatRecordService dyEvaluatesGreatRecordService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXCommonManage cxCommonManage;
    @Autowired
    private IArUserDailyRecordService dailyRecordService;
    @Autowired
    private IArUserSentimentRecordService sentimentRecordService;
    @Autowired
    private IArUserLevelRecordService levelRecordService;
    @Autowired
    private ISrLevelsService levelsService;

    public void publishInDyWork(arUserDyWorkDto dto, Long userId) {
        //1.插入动态作品数据
        ArUserDyWorks dyWorks = new ArUserDyWorks();
        BeanUtils.copyProperties(dto,dyWorks);
        dyWorks.setUserId(userId);
        SrArea area = areaService.selectById(dto.getCity());
        String center = area.getCenter();
        if(Strings.isNullOrEmpty(center)){
            throw new RuntimeException();
        }
        String[] coordinates = center.split(",");
        dyWorks.setLongitude(coordinates[0]);
        dyWorks.setLatitude(coordinates[1]);
        dyWorksService.insert(dyWorks);

        //2.插入动态作品图片
        List<ArUserDyPic> pics = Lists.newArrayList();
        for(String pic:dto.getPics()){
            ArUserDyPic pic1 = new ArUserDyPic();
            pic1.setDyWorksId(dyWorks.getId());
            pic1.setImg(pic);
            pics.add(pic1);
        }
        dyPicService.insertBatch(pics);

        //发布作品
        if(dto.getType()!=null && dto.getType()!=2){
            //当日新增人气
            LocalDate date =LocalDate.now();
            ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(userId,date);
            if(dailyRecord==null){
                dailyRecord = new ArUserDailyRecord();
                dailyRecord.setDailySentiment(contants.workNewLong);
                dailyRecord.setDailyNewWorks(1L);
                dailyRecord.setScoreNewWorks(contants.workNew);
                dailyRecord.setUserId(userId);
                dailyRecord.setNowDate(date);
            }else {
                dailyRecord.setDailySentiment(dailyRecord.getDailySentiment()+contants.workNewLong);
                dailyRecord.setDailyNewWorks(dailyRecord.getDailyNewWorks()+1);
                dailyRecord.setScoreNewWorks(dailyRecord.getScoreNewWorks()+contants.workNew);
            }
            dailyRecordService.insertOrUpdate(dailyRecord);

            //人气记录
            ArUserSentimentRecord sentimentRecord = new ArUserSentimentRecord();
            sentimentRecord.setUserId(userId);
            sentimentRecord.setNum(contants.workNew);
            sentimentRecord.setType(1);
            sentimentRecordService.insert(sentimentRecord);

            //修改用户表人气
            ArUser user = userService.selectById(userId);
            user.setSentimentNum(user.getSentimentNum()+contants.workNew);
            userService.updateById(user);
        }

        //发布动态
        if(dto.getType()!=null && dto.getType()==2){
            //判断当日是否因发过动态加过活跃值
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            map.put("category",1);
            map.put("type",4);
            ArUserLevelRecord levelRecord = levelRecordService.findOneByConditons(map);
            if(levelRecord==null){
                //当日新增活跃值
                LocalDate date =LocalDate.now();
                ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(userId,date);
                if(dailyRecord==null){
                    dailyRecord = new ArUserDailyRecord();
                    dailyRecord.setDailyActiveNum(contants.publishDyNum.longValue());
                    dailyRecord.setUserId(userId);
                    dailyRecord.setNowDate(date);
                }else {
                    dailyRecord.setDailyActiveNum(dailyRecord.getDailyActiveNum()+contants.publishDyNum.longValue());
                }
                dailyRecordService.insertOrUpdate(dailyRecord);

                //level记录
                levelRecord = new ArUserLevelRecord();
                levelRecord.setUserId(userId);
                levelRecord.setNum(contants.publishDyNum.intValue());
                levelRecord.setCategory(1);
                levelRecord.setType(4);
                levelRecordService.insert(levelRecord);

                //修改用户表level
                ArUser user = userService.selectById(userId);
                user.setActiveNum(user.getActiveNum()+contants.publishDyNum.intValue());
                SrLevels nextLevels = levelsService.findOneByLevel(user.getPerLevel()+1);
                if(nextLevels!=null){
                    Long allNum = user.getActiveNum()+user.getCharmNum()+user.getWealthNum();
                    if(allNum>=nextLevels.getScore()){
                        user.setPerLevel(user.getPerLevel()+1);
                    }
                }
                userService.updateById(user);
            }

        }

    }

    /**
     * 是否点赞
     * @param dyWorksId
     * @param userId
     * @return true-点过 flase 未点过
     */
    public boolean IsGreatForDyWorks(Long dyWorksId, Long userId) {
        ArUserDyGreatNum dyGreatNum = dyGreatNumService.findOneByDyWorksIdAndUserId(dyWorksId,userId);
        if(dyGreatNum==null){
            return false;
        }
        return true;
    }

    /**
     * 动态作品集点赞
     * @param dyWorksId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInDyWorks(Long dyWorksId, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyWorksId);
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);

        if(dyWorks==null){
            throw new RuntimeException();
        }

        //1.修改动态作品的点赞人数
        dyWorks.setGreatNum(dyWorks.getGreatNum()+1);
        dyWorksService.updateById(dyWorks);

        //2.添加点赞记录
        ArUserDyGreatRecord greatRecord = new ArUserDyGreatRecord();
        greatRecord.setDyWorksId(dyWorksId);
        greatRecord.setUserId(userId);
        greatRecord.setAuthorId(dyWorks.getUserId());
        dyGreatRecordService.insert(greatRecord);

        //3.修改或新增动态作品点赞次数记录
        ArUserDyGreatNum greatNum = dyGreatNumService.findOneByDyWorksIdAndUserId(dyWorksId,userId);
        if(greatNum==null){
            greatNum = new ArUserDyGreatNum();
            greatNum.setDyWorksId(dyWorksId);
            greatNum.setUserId(userId);
            greatNum.setAuthorId(dyWorks.getUserId());
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        dyGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        String orderNo = "";
        if(dyWorks.getType()==2){//动态
            orderNo = "F1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setModule(10);
        }else {//作品
            orderNo = "G1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setModule(12);
        }
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        if(dyWorks.getType()==2){//动态
            cxCommonManage.greatAction(userId,dyWorks.getUserId(),2);
        }else {//作品
            cxCommonManage.greatAction(userId,dyWorks.getUserId(),3);
        }

        Integer category = 0;
        if(dyWorks.getType()==2){//动态
            category = 5;
        }else {//作品
            category = 6;
        }
        final Integer category2 = category;

        ArUser author = userService.selectById(dyWorks.getUserId());
        String a = "@"+author.getAccount()+":内容:"+dyWorks.getContent()+"...";

        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,dyWorks.getUserId(),dyWorks.getId(),category2,a,dyWorksId);
            }
        }.start();

    }

    /**
     * 动态作品 评论
     * @param dyWorksId
     * @param content
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void evaluateInDyWorks(Long dyWorksId, String content, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyWorksId);
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);

        if(dyWorks==null){
            throw new RuntimeException();
        }

        //1.添加评价数据记录
        ArUserDyEvaluates evaluates = new ArUserDyEvaluates();
        evaluates.setDyWorksId(dyWorksId);
        evaluates.setUserId(userId);
        evaluates.setAuthorId(dyWorks.getUserId());
        evaluates.setType(1);
        evaluates.setLevel(1);
        evaluates.setReplyId(0L);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        dyEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_dy_works的message_num留言数
        dyWorks.setMessageNum(dyWorks.getMessageNum()+1);
        dyWorksService.updateById(dyWorks);

        ArUser author = userService.selectById(dyWorks.getUserId());
        String a = "@"+author.getAccount()+":内容:"+dyWorks.getContent()+"...";

        Integer category = 0;
        if(dyWorks.getType()==2){//动态
            category = 5;
        }else {//作品
            category = 6;
        }
        final Integer category2 = category;

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,dyWorks.getUserId(),content,evaluates.getId(),category2,a,dyWorks.getId());
            }
        }.start();
    }

    /**
     * 动态作品评论点赞
     * @param dyEvaluateId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInEvaluateForDyWorks(Long dyEvaluateId, Long userId)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("dyEvaluateId",dyEvaluateId);
        ArUserDyEvaluates dyEvaluates = dyEvaluatesService.findOneInSecondEvaluate(map4);
        if(dyEvaluates==null){
            throw new RuntimeException();
        }

        //1.修改动态作品评论的点赞人数
        dyEvaluates.setGreatNum(dyEvaluates.getGreatNum()+1);
        dyEvaluatesService.updateById(dyEvaluates);

        //2.添加点赞记录
        ArUserDyEvaluatesGreatRecord greatRecord = new ArUserDyEvaluatesGreatRecord();
        greatRecord.setDyEvaluateId(dyEvaluateId);
        greatRecord.setUserId(userId);
        greatRecord.setReplyId(dyEvaluates.getUserId());
        dyEvaluatesGreatRecordService.insert(greatRecord);

        //3.新增或修改点赞次数表
        ArUserDyEvaluatesGreatNum greatNum = dyEvaluatesGreatNumService.findOneByEvaluteIdAndUserId(dyEvaluateId,userId);
        if(greatNum==null){
            greatNum = new ArUserDyEvaluatesGreatNum();
            greatNum.setUserId(userId);
            greatNum.setDyEvaluateId(dyEvaluateId);
            greatNum.setReplyId(dyEvaluates.getUserId());
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        dyEvaluatesGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyEvaluates.getDyWorksId());
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);
        String orderNo = "";
        if(dyWorks.getType()==2){//动态
            orderNo = "F2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setModule(11);
        }else {//作品
            orderNo = "G2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            moneyRecord.setModule(13);
        }
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        if(dyWorks.getType()==2) {//动态
            cxCommonManage.greatAction(userId,dyEvaluates.getUserId(),2);
        }else {//作品
            cxCommonManage.greatAction(userId,dyEvaluates.getUserId(),3);
        }

        Integer category = 0;
        if(dyWorks.getType()==2){//动态评论
            category = 11;
        }else {//作品评论
            category = 12;
        }
        final Integer category2 = category;

        ArUser author = userService.selectById(dyWorks.getUserId());
        String a = "@"+author.getAccount()+":内容:"+dyWorks.getContent()+"...";

        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,dyWorks.getUserId(),dyWorks.getId(),category2,a,dyWorks.getId());
            }
        }.start();

    }

    /**
     * 动态作品评论回复
     * @param dyEvaluateId
     * @param userId
     * @param content
     * @throws Exception
     */
    @Transactional
    public void evaluateInEvaluateForDyWorks(Long dyEvaluateId, Long userId, String content)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("dyEvaluateId",dyEvaluateId);
        ArUserDyEvaluates dyEvaluates = dyEvaluatesService.findOneInSecondEvaluate(map4);
        if(dyEvaluates==null){
            throw new RuntimeException();
        }

        //1.添加新的评论记录
        ArUserDyEvaluates evaluates = new ArUserDyEvaluates();
        evaluates.setDyWorksId(dyEvaluates.getDyWorksId());
        evaluates.setUserId(userId);
        evaluates.setAuthorId(dyEvaluates.getAuthorId());
        evaluates.setType(2);
        evaluates.setLevel(dyEvaluates.getLevel()+1);
        evaluates.setReplyId(dyEvaluateId);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        dyEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_dy_evaluates的reply_num留言数
        dyEvaluates.setReplyNum(dyEvaluates.getReplyNum()+1);
        dyEvaluatesService.updateById(dyEvaluates);

        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyEvaluates.getDyWorksId());
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);

        ArUser author = userService.selectById(dyWorks.getUserId());
        String a = "@"+author.getAccount()+":内容:"+dyWorks.getContent()+"...";

        Integer category = 0;
        if(dyWorks.getType()==2){//动态评论
            category = 11;
        }else {//作品评论
            category = 12;
        }
        final Integer category2 = category;

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,dyEvaluates.getUserId(),content,evaluates.getId(),category2,a,dyWorks.getId());
            }
        }.start();
    }
}
