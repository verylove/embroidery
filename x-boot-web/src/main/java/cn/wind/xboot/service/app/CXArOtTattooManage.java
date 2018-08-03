package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.dto.app.ar.arUserOtTattooDto;
import cn.wind.xboot.enums.contants;
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
 * @Date: 2018/7/17 17:00
 * @Description:
 */
@Service
public class CXArOtTattooManage {

    @Autowired
    private IArUserOtTattooService otTattooService;
    @Autowired
    private IArUserOtPicService otPicService;
    @Autowired
    private IArUserOtGreatNumService otGreatNumService;
    @Autowired
    private IArUserOtGreatRecordService otGreatRecordService;
    @Autowired
    private IArUserOtEvaluatesService otEvaluatesService;
    @Autowired
    private IArUserOtEvaluatesGreatNumService otEvaluatesGreatNumService;
    @Autowired
    private IArUserOtEvaluatesGreatRecordService otEvaluatesGreatRecordService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXCommonManage cxCommonManage;

    /**
     *  纹纹达人发布
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publishOtTattoo(arUserOtTattooDto dto, Long userId)throws Exception {
        //1.插入纹纹达人数据
        ArUserOtTattoo otTattoo = new ArUserOtTattoo();
        BeanUtils.copyProperties(dto,otTattoo);
        if(dto.getPics()==null||dto.getPics().size()<1){
            otTattoo.setCoverImg(contants.defaultCoverImg);
        }else {
            otTattoo.setCoverImg(dto.getPics().get(0));
        }
        otTattoo.setUserId(userId);
        otTattooService.insert(otTattoo);

        //2.插入图片
        List<ArUserOtPic> pics = Lists.newArrayList();
        for(String pic:dto.getPics()){
            ArUserOtPic pic1 = new ArUserOtPic();
            pic1.setImg(pic);
            pic1.setOtTattooId(otTattoo.getId());
            pics.add(pic1);
        }
        otPicService.insertBatch(pics);
    }

    /**
     * 纹纹达人是否点赞
     * @param otTattooId
     * @param userId
     * @return true-点过 false-未点过
     */
    public boolean IsGreatForOtTattoo(Long otTattooId, Long userId) {
        ArUserOtGreatNum greatNum = otGreatNumService.findOneByOtTattooIdAndUserId(otTattooId,userId);
        if(greatNum==null){
            return false;
        }
        return true;
    }

    /**
     * 纹纹达人点赞
     * @param otTattooId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInOtTattoo(Long otTattooId, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("otTattooId",otTattooId);
        ArUserOtTattoo otTattoo = otTattooService.findOneByConditions(map);

        if(otTattoo==null){
            throw new RuntimeException();
        }

        //1.修改纹纹达人的点赞人数
        otTattoo.setGreatNum(otTattoo.getGreatNum()+1);
        otTattooService.updateById(otTattoo);

        //2.添加点赞记录
        ArUserOtGreatRecord greatRecord = new ArUserOtGreatRecord();
        greatRecord.setOtTattooId(otTattooId);
        greatRecord.setUserId(userId);
        greatRecord.setAuthorId(otTattoo.getUserId());
        otGreatRecordService.insert(greatRecord);

        //3.修改或新增纹纹达人点赞次数记录
        ArUserOtGreatNum greatNum = otGreatNumService.findOneByOtTattooIdAndUserId(otTattooId,userId);
        if(greatNum==null){
            greatNum = new ArUserOtGreatNum();
            greatNum.setOtTattooId(otTattooId);
            greatNum.setUserId(userId);
            greatNum.setAuthorId(userId);
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        otGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "E1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(8);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,otTattoo.getUserId(),1);
    }


    /**
     * 纹纹达人评论
     * @param otTattooId
     * @param content
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void evaluateInOtTattoo(Long otTattooId, String content, Long userId)throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        map.put("otTattooId",otTattooId);
        ArUserOtTattoo otTattoo = otTattooService.findOneByConditions(map);

        if(otTattoo==null){
            throw new RuntimeException();
        }

        //1.添加评价数据记录
        ArUserOtEvaluates evaluates = new ArUserOtEvaluates();
        evaluates.setOtTattooId(otTattooId);
        evaluates.setUserId(userId);
        evaluates.setAuthorId(otTattoo.getUserId());
        evaluates.setType(1);
        evaluates.setLevel(1);
        evaluates.setReplyId(0L);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        otEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_ot_tattoo的message_num留言数
        otTattoo.setMessageNum(otTattoo.getMessageNum()+1);
        otTattooService.updateById(otTattoo);
    }

    /**
     * 纹纹达人评论 点赞
     * @param otEvaluateId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInEvaluateForOtTattoo(Long otEvaluateId, Long userId)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("otEvaluateId",otEvaluateId);
        ArUserOtEvaluates otEvaluates = otEvaluatesService.findOneInSecondEvaluate(map4);
        if(otEvaluates==null){
            throw new RuntimeException();
        }

        //1.修改纹纹达人评论的点赞人数
        otEvaluates.setGreatNum(otEvaluates.getGreatNum()+1);
        otEvaluatesService.updateById(otEvaluates);

        //2.添加点赞记录
        ArUserOtEvaluatesGreatRecord greatRecord = new ArUserOtEvaluatesGreatRecord();
        greatRecord.setOtEvaluateId(otEvaluateId);
        greatRecord.setUserId(userId);
        greatRecord.setReplyId(otEvaluates.getUserId());
        otEvaluatesGreatRecordService.insert(greatRecord);

        //3.新增或修改点赞次数表
        ArUserOtEvaluatesGreatNum greatNum = otEvaluatesGreatNumService.findOneByEvaluteIdAndUserId(otEvaluateId,userId);
        if(greatNum==null){
            greatNum = new ArUserOtEvaluatesGreatNum();
            greatNum.setUserId(userId);
            greatNum.setOtEvaluateId(otEvaluateId);
            greatNum.setReplyId(otEvaluates.getUserId());
            greatNum.setNum(1);
        }else {
            greatNum.setNum(greatNum.getNum()+1);
        }
        otEvaluatesGreatNumService.insertOrUpdate(greatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "E2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(9);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

        cxCommonManage.greatAction(userId,otEvaluates.getUserId(),1);
    }

    /**
     * 纹纹达人评论回复
     * @param otEvaluateId
     * @param userId
     * @param content
     * @throws Exception
     */
    @Transactional
    public void evaluateInEvaluateForOtTattoo(Long otEvaluateId, Long userId, String content)throws Exception {
        Map<String,Object> map4= Maps.newHashMap();
        map4.put("otEvaluateId",otEvaluateId);
        ArUserOtEvaluates otEvaluates= otEvaluatesService.findOneInSecondEvaluate(map4);
        if(otEvaluates==null){
            throw new RuntimeException();
        }

        //1.添加新的评论记录
        ArUserOtEvaluates evaluates = new ArUserOtEvaluates();
        evaluates.setOtTattooId(otEvaluates.getOtTattooId());
        evaluates.setUserId(userId);
        evaluates.setAuthorId(otEvaluates.getAuthorId());
        evaluates.setType(2);
        evaluates.setLevel(otEvaluates.getLevel()+1);
        evaluates.setReplyId(otEvaluateId);
        evaluates.setContent(content);
        evaluates.setReplyNum(0);
        evaluates.setGreatNum(0L);
        otEvaluatesService.insert(evaluates);

        //2.修改cx_ar_user_ot_evaluates的reply_num留言数
        otEvaluates.setReplyNum(otEvaluates.getReplyNum()+1);
        otEvaluatesService.updateById(otEvaluates);
    }
}
