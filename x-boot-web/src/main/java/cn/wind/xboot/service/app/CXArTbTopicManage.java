package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.dto.app.ar.arUserTbTopicDto;
import cn.wind.xboot.enums.contants;
import cn.wind.xboot.tencent.thread.MyEvaluateThread;
import cn.wind.xboot.tencent.thread.MyGreatThread;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/16 20:21
 * @Description:
 */
@Service
public class CXArTbTopicManage {

    @Autowired
    private IArUserTbTopicService tbTopicService;
    @Autowired
    private IArUserTbPicService tbPicService;
    @Autowired
    private IArUserTbGreatNumService tbGreatNumService;
    @Autowired
    private IArUserTbGreatRecordService tbGreatRecordService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserTbEvaluatesService tbEvaluatesService;
    @Autowired
    private IArUserTbEvaluatesGreatNumService tbEvaluatesGreatNumService;
    @Autowired
    private IArUserTbEvaluatesGreatRecordService tbEvaluatesGreatRecordService;
    @Autowired
    private CXCommonManage cxCommonManage;
    
    /**
     * 发布帖子话题
     * @param dto
     * @throws Exception
     */
    @Transactional
    public void publishTbTopic(arUserTbTopicDto dto,Long userId)throws Exception {
        //1.插入话题帖子数据
        ArUserTbTopic tbTopic = new ArUserTbTopic();
        BeanUtils.copyProperties(dto,tbTopic);
        tbTopic.setUserId(userId);
        if(dto.getPics()!=null && dto.getPics().size()>0){
            tbTopic.setCoverImg(dto.getPics().get(0));
        }else {
            tbTopic.setCoverImg(contants.defaultCoverImg);
        }
        tbTopicService.insert(tbTopic);
        
        //2.插入话题帖子图片数据
        List<ArUserTbPic> pics = new ArrayList<>();
        for(String pic:dto.getPics()){
            ArUserTbPic pic1 = new ArUserTbPic();
            pic1.setImg(pic);
            pic1.setTbTopicId(tbTopic.getId());
            pics.add(pic1);
        }
        tbPicService.insertBatch(pics);
    }

    /**
     * 是否点过赞
     * @param tbTopicId
     * @param userId
     * @return
     */
    public boolean IsGreatForTbTopic(Long tbTopicId, Long userId) {
        ArUserTbGreatNum greatNum = tbGreatNumService.findOneByTopicIdAndUserId(tbTopicId,userId);
        if(greatNum==null){
            return false;
        }
        return true;
    }

    /**
     * 贴吧话题点赞
     * @param tbTopicId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInTbTopic(Long tbTopicId, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbTopicId);
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);
        if(tbTopic==null){
            throw new RuntimeException();
        }

        //1.修改贴吧话题的点赞人数
        tbTopic.setGreatNum(tbTopic.getGreatNum()+1);
        tbTopicService.updateById(tbTopic);

        //2.添加点赞记录
        ArUserTbGreatRecord greatRecord = new ArUserTbGreatRecord();
        greatRecord.setTbTopicId(tbTopicId);
        greatRecord.setUserId(userId);
        greatRecord.setAuthorId(tbTopic.getUserId());
        tbGreatRecordService.insert(greatRecord);

        //3.修改或新增贴吧话题点赞次数记录
        ArUserTbGreatNum greatNum = tbGreatNumService.findOneByTopicIdAndUserId(tbTopicId,userId);
        if(greatNum==null){
            greatNum = new ArUserTbGreatNum();
            greatNum.setAuthorId(tbTopic.getUserId());
            greatNum.setTbTopicId(tbTopicId);
            greatNum.setUserId(userId);
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        tbGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "D1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(6);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,tbTopic.getUserId(),1);

        ArUser author = userService.selectById(tbTopic.getUserId());
        String a = "@"+author.getAccount()+":内容:"+tbTopic.getContent()+"...";
        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,tbTopic.getUserId(),tbTopic.getId(),3,a,tbTopicId);
            }
        }.start();
    }

    /**
     * 话题贴吧评论
     * @param tbTopicId
     * @param content
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void evaluateInTbTopic(Long tbTopicId, String content, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbTopicId);
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);
        if(tbTopic==null){
            throw new RuntimeException();
        }

        //1.添加评价数据记录
        ArUserTbEvaluates evaluates = new ArUserTbEvaluates();
        evaluates.setTbTopicId(tbTopicId);
        evaluates.setUserId(userId);
        evaluates.setAuthorId(tbTopic.getUserId());
        evaluates.setType(1);
        evaluates.setLevel(1);
        evaluates.setReplyId(0L);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        tbEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_tb_topic的message_num留言数
        tbTopic.setMessageNum(tbTopic.getMessageNum()+1);
        tbTopicService.updateById(tbTopic);

        ArUser author = userService.selectById(tbTopic.getUserId());
        String a = "@"+author.getAccount()+":内容:"+tbTopic.getContent()+"...";

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,tbTopic.getUserId(),content,evaluates.getId(),3,a,tbTopicId);
            }
        }.start();
    }

    /**
     * 贴吧话题评论回复
     * @param tbEvaluateId
     * @param content
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void evaluateInEvaluateForTbTopic(Long tbEvaluateId, String content, Long userId)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("tbEvaluateId",tbEvaluateId);
        ArUserTbEvaluates tbEvaluates = tbEvaluatesService.findOneInSecondEvaluate(map4);
        if(tbEvaluates==null){
            throw new RuntimeException();
        }

        //1.添加新的评论记录
        ArUserTbEvaluates evaluates = new ArUserTbEvaluates();
        evaluates.setTbTopicId(tbEvaluates.getTbTopicId());
        evaluates.setUserId(userId);
        evaluates.setAuthorId(tbEvaluates.getAuthorId());
        evaluates.setType(2);
        evaluates.setLevel(tbEvaluates.getLevel()+1);
        evaluates.setReplyId(tbEvaluateId);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        tbEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_tb_evaluates的reply_num留言数
        tbEvaluates.setReplyNum(tbEvaluates.getReplyNum()+1);
        tbEvaluatesService.updateById(tbEvaluates);

        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbEvaluates.getTbTopicId());
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);

        ArUser author = userService.selectById(tbTopic.getUserId());
        String a = "@"+author.getAccount()+":内容:"+tbTopic.getContent()+"...";

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,tbTopic.getUserId(),content,evaluates.getId(),9,a,tbTopic.getId());
            }
        }.start();
    }

    /**
     * 贴吧话题评论点赞
     * @param tbEvaluateId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInEvaluateForTbTopic(Long tbEvaluateId, Long userId)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("tbEvaluateId",tbEvaluateId);
        ArUserTbEvaluates tbEvaluates = tbEvaluatesService.findOneInSecondEvaluate(map4);
        if(tbEvaluates==null){
            throw new RuntimeException();
        }

        //1.修改贴吧话题评论的点赞人数
        tbEvaluates.setGreatNum(tbEvaluates.getGreatNum()+1);
        tbEvaluatesService.updateById(tbEvaluates);

        //2.添加点赞记录
        ArUserTbEvaluatesGreatRecord greatRecord = new ArUserTbEvaluatesGreatRecord();
        greatRecord.setTbEvaluateId(tbEvaluateId);
        greatRecord.setUserId(userId);
        greatRecord.setReplyId(tbEvaluates.getUserId());
        tbEvaluatesGreatRecordService.insert(greatRecord);

        //3.新增或修改点赞次数表
        ArUserTbEvaluatesGreatNum greatNum = tbEvaluatesGreatNumService.findOneByEvaluteIdAndUserId(tbEvaluateId,userId);
        if(greatNum==null){
            greatNum = new ArUserTbEvaluatesGreatNum();
            greatNum.setUserId(userId);
            greatNum.setTbEvaluateId(tbEvaluateId);
            greatNum.setReplyId(tbEvaluates.getUserId());
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        tbEvaluatesGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "D2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(7);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,tbEvaluates.getUserId(),1);

        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbEvaluates.getTbTopicId());
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);

        ArUser author = userService.selectById(tbTopic.getUserId());
        String a = "@"+author.getAccount()+":内容:"+tbTopic.getContent()+"...";
        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,tbTopic.getUserId(),tbTopic.getId(),9,a,tbTopic.getId());
            }
        }.start();
    }
}
