package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.dto.app.ar.arUserSkGalleryDto;
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
 * @Date: 2018/7/13 15:13
 * @Description:
 */
@Service
public class CXArSkGalleryManage {

    @Autowired
    private IArUserSkGalleryService skGalleryService;
    @Autowired
    private IArUserSkPicService skPicService;
    @Autowired
    private IArUserSkGreatNumService skGreatNumService;
    @Autowired
    private IArUserSkGreatRecordService skGreatRecordService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserSkEvaluatesService skEvaluatesService;
    @Autowired
    private IArUserSkEvaluatesGreatRecordService skEvaluatesGreatRecordService;
    @Autowired
    private IArUserSkEvaluatesGreatNumService skEvaluatesGreatNumService;
    @Autowired
    private CXCommonManage cxCommonManage;


    /**
     * 图库找图发布
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publishSkGallery(arUserSkGalleryDto dto, Long userId)throws Exception {
        //1.插入图库找图数据
        ArUserSkGallery skGallery = new ArUserSkGallery();
        BeanUtils.copyProperties(dto,skGallery);
        skGallery.setUserId(userId);
        if(dto.getPics()!=null && dto.getPics().size()>0){
            skGallery.setCoverImg(dto.getPics().get(0));
        }else {
            skGallery.setCoverImg(contants.defaultCoverImg);
        }
        skGalleryService.insert(skGallery);

        //2.插入图库找图图片数据
        List<ArUserSkPic> pics = new ArrayList<>();
        for(String pic:dto.getPics()){
            ArUserSkPic pic1 = new ArUserSkPic();
            pic1.setImg(pic);
            pic1.setSeekGalleryId(skGallery.getId());
            pics.add(pic1);
        }
        skPicService.insertBatch(pics);

    }

    /**
     * 是否给该图库找图点过赞
     * @param skGalleryId
     * @param userId
     * @return true-点过 false-未点过
     */
    public boolean IsGreatForSkGallery(Long skGalleryId, Long userId) {
        //1.检索cx_ar_user_sk_great_num数据
        ArUserSkGreatNum skGreatNum = skGreatNumService.findOneByGalleryIdAndUserId(skGalleryId,userId);
        if(skGreatNum==null){//未点过赞
            return false;
        }
        //点过赞
        return true;
    }

    /**
     * 图库找图点赞
     * @param skGalleryId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInSkGallery(Long skGalleryId, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skGalleryId);
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);

        if(skGallery==null){
            throw new RuntimeException();
        }

        //1.修改图库找图的点赞人数
        skGallery.setGreatNum(skGallery.getGreatNum()+1);
        skGalleryService.updateById(skGallery);

        //2.添加点赞记录
        ArUserSkGreatRecord greatRecord = new ArUserSkGreatRecord();
        greatRecord.setSeekGalleryId(skGalleryId);
        greatRecord.setUserId(userId);
        greatRecord.setAuthorId(skGallery.getUserId());
        skGreatRecordService.insert(greatRecord);

        //3.修改或新增图库找图点赞次数记录
        ArUserSkGreatNum greatNum = skGreatNumService.findOneByGalleryIdAndUserId(skGalleryId,userId);
        if(greatNum==null){
            greatNum = new ArUserSkGreatNum();
            greatNum.setSeekGalleryId(skGalleryId);
            greatNum.setUserId(userId);
            greatNum.setAuthorId(userId);
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        skGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "C1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(4);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,skGallery.getUserId(),1);

        ArUser author = userService.selectById(skGallery.getUserId());
        String a = "@"+author.getAccount()+":内容:"+skGallery.getContent()+"...";

        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,skGallery.getUserId(),skGallery.getId(),2,a,skGalleryId);
            }
        }.start();
    }

    /**
     * 图库找图评论
     * @param skGalleryId
     * @param content
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void evaluateInSkGallery(Long skGalleryId, String content, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skGalleryId);
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);

        if(skGallery==null){
            throw new RuntimeException();
        }

        //1.添加评价数据记录
        ArUserSkEvaluates evaluates = new ArUserSkEvaluates();
        evaluates.setSeekGalleryId(skGalleryId);
        evaluates.setUserId(userId);
        evaluates.setAuthorId(skGallery.getUserId());
        evaluates.setType(1);
        evaluates.setLevel(1);
        evaluates.setReplyId(0L);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        skEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_sk_gallery的message_num留言数
        skGallery.setMessageNum(skGallery.getMessageNum()+1);
        skGalleryService.updateById(skGallery);

        ArUser author = userService.selectById(skGallery.getUserId());
        String a = "@"+author.getAccount()+":内容:"+skGallery.getContent()+"...";

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,skGallery.getUserId(),content,evaluates.getId(),2,a,skGalleryId);
            }
        }.start();
    }

    /**
     * 点赞图库找图的评论
     * @param skEvaluateId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInEvaluateForSkGallery(Long skEvaluateId, Long userId)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("skEvaluateId",skEvaluateId);
        ArUserSkEvaluates skEvaluates = skEvaluatesService.findOneInSecondEvaluate(map4);
        if(skEvaluates==null){
            throw new RuntimeException();
        }

        //1.修改图库找图评论的点赞人数
        skEvaluates.setGreatNum(skEvaluates.getGreatNum()+1);
        skEvaluatesService.updateById(skEvaluates);

        //2.添加点赞记录
        ArUserSkEvaluatesGreatRecord greatRecord = new ArUserSkEvaluatesGreatRecord();
        greatRecord.setSkEvaluateId(skEvaluateId);
        greatRecord.setUserId(userId);
        greatRecord.setReplyId(skEvaluates.getUserId());
        skEvaluatesGreatRecordService.insert(greatRecord);

        //3.新增或修改点赞次数表
        ArUserSkEvaluatesGreatNum greatNum = skEvaluatesGreatNumService.findOneByEvaluteIdAndUserId(skEvaluateId,userId);
        if(greatNum==null){
            greatNum = new ArUserSkEvaluatesGreatNum();
            greatNum.setUserId(userId);
            greatNum.setSkEvaluateId(skEvaluateId);
            greatNum.setReplyId(skEvaluates.getUserId());
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        skEvaluatesGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "C2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(5);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,skEvaluates.getUserId(),1);

        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skEvaluates.getSeekGalleryId());
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);

        ArUser author = userService.selectById(skGallery.getUserId());
        String a = "@"+author.getAccount()+":内容:"+skGallery.getContent()+"...";

        //6.推送
        final MyGreatThread greatThread = new MyGreatThread();
        new Thread(){
            @Override
            public void run(){
                greatThread.myGreatThread(userId,skGallery.getUserId(),skGallery.getId(),8,a,skGallery.getId());
            }
        }.start();
    }

    /**
     * 图库找图评论回复
     * @param skEvaluateId
     * @param userId
     * @param content
     * @throws Exception
     */
    @Transactional
    public void evaluateInEvaluateForSkGallery(Long skEvaluateId, Long userId, String content)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("skEvaluateId",skEvaluateId);
        ArUserSkEvaluates skEvaluates = skEvaluatesService.findOneInSecondEvaluate(map4);
        if(skEvaluates==null){
            throw new RuntimeException();
        }

        //1.添加新的评论记录
        ArUserSkEvaluates evaluates = new ArUserSkEvaluates();
        evaluates.setSeekGalleryId(skEvaluates.getSeekGalleryId());
        evaluates.setUserId(userId);
        evaluates.setAuthorId(skEvaluates.getAuthorId());
        evaluates.setType(2);
        evaluates.setLevel(skEvaluates.getLevel()+1);
        evaluates.setReplyId(skEvaluateId);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        skEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_sk_evaluates的reply_num留言数
        skEvaluates.setReplyNum(skEvaluates.getReplyNum()+1);
        skEvaluatesService.updateById(skEvaluates);

        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skEvaluates.getSeekGalleryId());
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);

        ArUser author = userService.selectById(skGallery.getUserId());
        String a = "@"+author.getAccount()+":内容:"+skGallery.getContent()+"...";

        //3.推送
        final MyEvaluateThread evaluateThread = new MyEvaluateThread();
        new Thread(){
            @Override
            public void run(){
                evaluateThread.myEvaluateThread(userId,skGallery.getUserId(),content,evaluates.getId(),8,a,skGallery.getId());
            }
        }.start();
    }

}
