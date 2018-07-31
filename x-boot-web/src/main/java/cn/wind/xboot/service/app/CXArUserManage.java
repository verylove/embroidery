package cn.wind.xboot.service.app;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.ml.entity.MlUserGoodsLogistics;
import cn.wind.db.ml.service.IMlUserGoodsLogisticsService;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrLevelsService;
import cn.wind.xboot.enums.contants;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/6 19:23
 * @Description:用户信息
 */
@Service
@Transactional
public class CXArUserManage {

    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserFollowsService followsService;
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
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IMlUserGoodsLogisticsService logisticsService;

    public ArUser addUserByIdentity(String phone, String password, Integer type) {

        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPhone(phone);
        user.setIcon(
                contants.defaultHeadImg);
        user.setPassword(password);
        user.setIdentity(type);
        userService.insert(user);
        return user;
    }

    public ArUser addUser(String openId, String phone, Integer type) {
        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPhone(phone);
        user.setPassword(contants.defaultPassword);
        user.setIcon(
                contants.defaultHeadImg);
        if(type==1){
            user.setWxOpenId(openId);
        }else {
            user.setQqOpenId(openId);
        }
        userService.insert(user);
        return user;
    }

    public ArUser updateUserWithOpenId(String openId, ArUser user, Integer type) {
        if(type==1){
            user.setWxOpenId(openId);
        }else {
            user.setQqOpenId(openId);
        }
        userService.updateById(user);
        return user;
    }

    public void updateUserWithPassword(String phone,String password) {
        ArUser user = userService.findOneByPhone(phone);
        user.setPassword(password);
        userService.updateById(user);
    }

    /**
     * 用户在该APP是否点过赞
     * @param userId
     * @return true-点过 false-未点过
     */
    public boolean IsGreatInApp(Long userId){
        ArUser user = userService.selectById(userId);
        Integer greatStatus = user.getGreatStatus();
        if(greatStatus==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 该用户的账户余额是否充足（是否可以进行本次消费）
     * @param userId
     * @param cost 消费的金额（刺币）
     * @return true-金额充足 false-金额不够
     */
    public boolean IsEnoughForBalance(Long userId, BigDecimal cost){
        ArUser user = userService.selectById(userId);
        BigDecimal balance = user.getBalance();
        if(balance.compareTo(cost)<0){
            return false;
        }
        return true;
    }

    /**
     * 是否实名认证
     * @param userId
     * @return true实名认证；false未认证
     */
    public boolean IsNameAduit(Long userId){
        ArUser user = userService.selectById(userId);
        if(user.getNameStatus()==3){
            return true;
        }
        return false;
    }

    /**
     * 是否关注
     * @param userId 关注主动者的ID
     * @param followId 被关注的人ID
     * @return true-关注 false-未关注
     */
    public boolean IsFollowInUserId(Long userId,Long followId){
        ArUserFollows follows = followsService.findOneByUserIdAndFollowId(userId,followId);
        if(follows==null){
            return false;
        }else if(follows.getStatus()==0){
            return false;
        }
        return true;
    }

    /**
     * 关注操作
     * @param followId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void followOneUser(Long followId, Long userId)throws Exception {
        ArUserFollows follows = followsService.findOneByUserIdAndFollowId(userId,followId);
        //关注人
        LocalDate date =LocalDate.now();
        ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(userId,date);
        //被关注的人
        ArUserDailyRecord dailyRecord2 = dailyRecordService.findOneByUserIdAndDate(followId,date);
        if(follows!=null&&follows.getStatus()==1){//取消关注
            follows.setStatus(0);
            followsService.updateById(follows);

            ArUserSentimentRecord sentimentRecord = new ArUserSentimentRecord();
            sentimentRecord.setUserId(followId);
            sentimentRecord.setNum(contants.follows);
            sentimentRecord.setType(6);
            sentimentRecordService.insert(sentimentRecord);

            //关注的人
            ArUser user1 = userService.selectById(userId);
            user1.setFocusNum(user1.getFocusNum()-1);
            userService.updateById(user1);

            ArUser user2 = userService.selectById(followId);
            user2.setFollowNum(user2.getFollowNum()-1);
            user2.setSentimentNum(user2.getSentimentNum()-contants.follows);
            userService.updateById(user2);
        }else {//关注
            if(follows==null){
                follows = new ArUserFollows();
                follows.setUserId(userId);
                follows.setFollowId(followId);
                follows.setStatus(1);
                followsService.insert(follows);
            }else {
                follows.setStatus(1);
                followsService.updateById(follows);
            }

            //关注的人
            if(dailyRecord==null){
                dailyRecord = new ArUserDailyRecord();
                dailyRecord.setNowDate(date);
                dailyRecord.setUserId(userId);
                dailyRecord.setDailyFocus(1L);
                dailyRecordService.insert(dailyRecord);
            }else {
                dailyRecord.setDailyFocus(dailyRecord.getDailyFocus()+1);
                dailyRecordService.updateById(dailyRecord);
            }

            //被关注的人
            if(dailyRecord2==null){
                dailyRecord2 = new ArUserDailyRecord();
                dailyRecord2.setNowDate(date);
                dailyRecord2.setUserId(followId);
                dailyRecord2.setDailyFollow(1L);
                dailyRecord2.setDailySentiment(contants.followsLong);
                dailyRecord2.setScoreFollow(contants.follows);
                dailyRecord2.setDailyCharmNum(contants.followLevel.longValue());
                dailyRecordService.insert(dailyRecord2);
            }else {
                dailyRecord2.setDailyFollow(dailyRecord2.getDailyFollow()+1);
                dailyRecord2.setDailySentiment(dailyRecord2.getDailySentiment()+contants.followsLong);
                dailyRecord2.setScoreFollow(dailyRecord2.getScoreFollow()+contants.follows);
                dailyRecord2.setDailyCharmNum(dailyRecord2.getDailyCharmNum()+contants.followLevel);
                dailyRecordService.updateById(dailyRecord2);
            }

            ArUserSentimentRecord sentimentRecord = new ArUserSentimentRecord();
            sentimentRecord.setUserId(followId);
            sentimentRecord.setNum(contants.follows);
            sentimentRecord.setType(5);
            sentimentRecordService.insert(sentimentRecord);

            ArUserLevelRecord levelRecord = new ArUserLevelRecord();
            levelRecord.setUserId(followId);
            levelRecord.setNum(contants.followLevel);
            levelRecord.setCategory(2);
            levelRecord.setType(5);
            levelRecordService.insert(levelRecord);

            //关注的人
            ArUser user1 = userService.selectById(userId);
            user1.setFocusNum(user1.getFocusNum()+1);
            userService.updateById(user1);

            ArUser user2 = userService.selectById(followId);
            user2.setFollowNum(user2.getFollowNum()+1);
            user2.setSentimentNum(user2.getSentimentNum()+contants.follows);
            user2.setCharmNum(user2.getCharmNum()+contants.followLevel);
            SrLevels nextLevels = levelsService.findOneByLevel(user2.getPerLevel()+1);
            if(nextLevels!=null){
                Long allNum = user2.getActiveNum()+user2.getCharmNum()+user2.getWealthNum();
                if(allNum>=nextLevels.getScore()){
                    user2.setPerLevel(user2.getPerLevel()+1);
                }
            }
            userService.updateById(user2);

        }
    }

    /**
     * 获取某用户关注的所有用户
     * @param userId
     * @return
     */
    public List<Long> getFollowIds(Long userId){
        return followsService.findAllFollowIdsByUserId(userId);
    }

    /**
     * 是否给默认点过赞
     * @param userId
     * @param greatId
     * @return true-点过 false-未点过
     */
    public boolean IsgreatInAppForOneUser(Long userId,Long greatId){
        ArUserGreats greats = greatsService.findByUserIdAndGreatId(userId,greatId);
        if(greats==null){
            return false;
        }
        return true;
    }

    /**
     * 支付结果
     * @param moneyRecord
     * @param user
     * @return
     */
    @Transactional
    public boolean balancePay(ArUserMoneyRecord moneyRecord, ArUser user) {
        try{
            //1.修改订单状态
            moneyRecord.setStatus(2);
            moneyRecordService.updateById(moneyRecord);

            //2.添加物流表信息
            MlUserGoodsLogistics logistics = new MlUserGoodsLogistics();
            logistics.setUserId(moneyRecord.getUserId());
            logistics.setOrderNo(moneyRecord.getOrderNo());
            logistics.setStatus(1);
            logisticsService.insert(logistics);

            //3.扣除余额
            user.setBalance(user.getBalance().subtract(moneyRecord.getAmount()));
            userService.updateById(user);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public void setPayPss(ArUser user, String payPass) {
        user.setPayPass(payPass);
        userService.updateById(user);
    }
}
