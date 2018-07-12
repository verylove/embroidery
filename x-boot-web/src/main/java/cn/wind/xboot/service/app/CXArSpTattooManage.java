package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.dto.app.ar.arUserSpTattooDto;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 19:40
 * @Description:
 */
@Service
public class CXArSpTattooManage {

    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserSpTattooService spTattooService;
    @Autowired
    private IArUserSpPicService spPicService;
    @Autowired
    private IArUserSpGreatNumService spGreatNumService;
    @Autowired
    private IArUserSpGreatRecordService spGreatRecordService;
    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserSpEvaluatesService spEvaluatesService;
    @Autowired
    private IArUserSpEvaluatesGreatNumService spEvaluatesGreatNumService;
    @Autowired
    private IArUserSpEvaluatesGreatRecordService spEvaluatesGreatRecordService;

    /**
     * 特价纹身发布（纹身师）
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publish(arUserSpTattooDto dto, Long userId)throws Exception {
        //1.插入特价纹身数据
        ArUserSpTattoo spTattoo = new ArUserSpTattoo();
        BeanUtils.copyProperties(dto, spTattoo);
        spTattoo.setUserId(userId);
        SrArea area = areaService.selectById(dto.getCity());
        String center = area.getCenter();
        if(Strings.isNullOrEmpty(center)){
            throw new RuntimeException();
        }
        String[] coordinates = center.split(",");
        spTattoo.setLongitude(coordinates[0]);
        spTattoo.setLatitude(coordinates[1]);
        spTattooService.insert(spTattoo);

        //2.插入特价纹身图片数据
        List<ArUserSpPic> pics = new ArrayList<>();
        for(String pic:dto.getPics()){
            ArUserSpPic pic1 = new ArUserSpPic();
            pic1.setImg(pic);
            pic1.setSpecialTattooId(spTattoo.getId());
            pics.add(pic1);
        }
        spPicService.insertBatch(pics);
    }

    /**
     * 是否给该特价纹身点过赞
     * @param spTattooId
     * @param userId
     * @return true-点过 false-未点过
     */
    public boolean IsGreatForSpTattoo(Long spTattooId, Long userId){
        //1.检索cx_ar_user_sp_great_num数据
        ArUserSpGreatNum spGreatNum = spGreatNumService.findOneByTattooIdAndUserId(spTattooId,userId);
        if(spGreatNum==null){//未点过赞
            return false;
        }
        //点过赞
        return true;
    }

    /**
     * 特价纹身点赞
     * @param spTattooId
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void greatInSpTattoo(Long spTattooId, Long userId)throws Exception{
        ArUserSpTattoo tattoo = spTattooService.findOneById(spTattooId);
        if(tattoo==null){
            throw new RuntimeException();
        }
        //1.修改特价纹身点赞人数
        tattoo.setGreatNum(tattoo.getGreatNum()+1);
        spTattooService.updateById(tattoo);

        //2.添加点赞记录
        ArUserSpGreatRecord spGreatRecord = new ArUserSpGreatRecord();
        spGreatRecord.setUserId(userId);
        spGreatRecord.setAuthorId(tattoo.getUserId());
        spGreatRecord.setSpecialTattooId(spTattooId);
        spGreatRecordService.insert(spGreatRecord);

        //3.添加次数记录
        ArUserSpGreatNum spGreatNum = spGreatNumService.findOneByTattooIdAndUserId(spTattooId,userId);
        if(spGreatNum==null){
            spGreatNum = new ArUserSpGreatNum();
            spGreatNum.setAuthorId(tattoo.getUserId());
            spGreatNum.setSpecialTattooId(spTattooId);
            spGreatNum.setUserId(userId);
            spGreatNum.setNum(1);
        }else {
            spGreatNum.setNum(spGreatNum.getNum()+1);
        }
        spGreatNumService.insertOrUpdate(spGreatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "B1"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(2);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

    }

    /**
     * 特价纹身评价
     * @param spTattooId
     * @param userId
     * @param content
     * @throws Exception
     */
    @Transactional
    public void evaluateInSpTattoo(Long spTattooId, Long userId, String content)throws Exception {
        ArUserSpTattoo tattoo = spTattooService.findOneById(spTattooId);
        if(tattoo==null){
            throw new RuntimeException();
        }
        //1.添加评价数据记录
        ArUserSpEvaluates spEvaluates = new ArUserSpEvaluates();
        spEvaluates.setSpecialTattooId(spTattooId);
        spEvaluates.setUserId(userId);
        spEvaluates.setAuthorId(tattoo.getUserId());
        spEvaluates.setType(1);//评价特价纹身
        spEvaluates.setLevel(1);//1-评论特价纹身
        spEvaluates.setReplyId(0L);
        spEvaluates.setContent(content);
        spEvaluatesService.insert(spEvaluates);

        //2.修改cx_ar_user_sp_tattoo的message_num留言数
        tattoo.setMessageNum(tattoo.getMessageNum()+1);
        spTattooService.updateById(tattoo);
    }

    /**
     * 特价纹身评论点赞
     * @param spEvaluateId
     * @param userId
     */
    @Transactional
    public void greatInEvaluateForSpTattoo(Long spEvaluateId, Long userId)throws Exception {
        ArUserSpEvaluates spEvaluates = spEvaluatesService.selectById(spEvaluateId);
        if(spEvaluates==null){
            throw new RuntimeException();
        }

        //1.修改该评论的点赞人数
        spEvaluates.setGreatNum(spEvaluates.getGreatNum()+1);
        spEvaluatesService.updateById(spEvaluates);

        //2.添加点赞记录
        ArUserSpEvaluatesGreatRecord spEvaluatesGreatRecord = new ArUserSpEvaluatesGreatRecord();
        spEvaluatesGreatRecord.setSpEvaluateId(spEvaluateId);
        spEvaluatesGreatRecord.setUserId(userId);
        spEvaluatesGreatRecord.setReplyId(spEvaluates.getUserId());
        spEvaluatesGreatRecordService.insert(spEvaluatesGreatRecord);

        //3.添加点赞次数记录
        ArUserSpEvaluatesGreatNum spEvaluatesGreatNum = spEvaluatesGreatNumService.findOneByEvaluteIdAndUserId(spEvaluateId,userId);
        if(spEvaluatesGreatNum==null){
            spEvaluatesGreatNum = new ArUserSpEvaluatesGreatNum();
            spEvaluatesGreatNum.setSpEvaluateId(spEvaluateId);
            spEvaluatesGreatNum.setUserId(userId);
            spEvaluatesGreatNum.setReplyId(spEvaluates.getUserId());
            spEvaluatesGreatNum.setNum(1);
        }else {
            spEvaluatesGreatNum.setNum(spEvaluatesGreatNum.getNum()+1);
        }
        spEvaluatesGreatNumService.insertOrUpdate(spEvaluatesGreatNum);

        //4.添加消费记录表
        BigDecimal cost = new BigDecimal("1.00");
        String orderNo = "B2"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        ArUserMoneyRecord moneyRecord = new ArUserMoneyRecord();
        moneyRecord.setUserId(userId);
        moneyRecord.setAmount(cost);
        moneyRecord.setType(2);//消费
        moneyRecord.setModule(3);
        moneyRecord.setOrderNo(orderNo);
        moneyRecord.setStatus(2);
        moneyRecord.setWay(4);//刺币
        moneyRecordService.insert(moneyRecord);

        //5.修改个人余额以及是否第一次点赞
        ArUser user = userService.selectById(userId);
        user.setBalance(user.getBalance().subtract(cost));
        user.setGreatStatus(1);
        userService.updateById(user);

    }

    /**
     * 特价纹身评价回复
     * @param spEvaluateId
     * @param userId
     * @param content
     * @throws Exception
     */
    @Transactional
    public void evaluateInEvaluateForSpTattoo(Long spEvaluateId, Long userId, String content)throws Exception {
        ArUserSpEvaluates spEvaluates = spEvaluatesService.selectById(spEvaluateId);
        if(spEvaluates==null){
            throw new RuntimeException();
        }
        //1.添加回复记录
        ArUserSpEvaluates replyEvaluate = new ArUserSpEvaluates();
        replyEvaluate.setSpecialTattooId(spEvaluates.getSpecialTattooId());
        replyEvaluate.setUserId(userId);
        replyEvaluate.setAuthorId(spEvaluates.getAuthorId());
        replyEvaluate.setType(2);
        replyEvaluate.setLevel(spEvaluates.getLevel()+1);
        replyEvaluate.setReplyId(spEvaluateId);
        replyEvaluate.setContent(content);
        replyEvaluate.setReplyNum(0);
        replyEvaluate.setGreatNum(0L);
        spEvaluatesService.insert(replyEvaluate);

        //2.修改cx_ar_user_sp_evaluates的reply_num留言数
        spEvaluates.setReplyNum(spEvaluates.getReplyNum()+1);
        spEvaluatesService.updateById(spEvaluates);
    }
}
