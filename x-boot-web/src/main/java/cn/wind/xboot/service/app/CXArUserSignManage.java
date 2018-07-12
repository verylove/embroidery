package cn.wind.xboot.service.app;

import cn.wind.common.utils.DateUtil;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserSignNum;
import cn.wind.db.ar.entity.ArUserSignRecord;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ar.service.IArUserSignNumService;
import cn.wind.db.ar.service.IArUserSignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 09:09
 * @Description:用户签到
 */
@Service
@Transactional
public class CXArUserSignManage {

    @Autowired
    private IArUserSignRecordService signRecordService;
    @Autowired
    private IArUserSignNumService signNumService;
    @Autowired
    private IArUserService userService;

    /**
     * 签到
     * @param userId
     */
    public Map<String,Object> signIn(Long userId)throws Exception {
        Map<String,Object> map = new HashMap<>();
        int signDays = 0;
        ArUser user = userService.selectById(userId);
        if(user==null){
            throw new Exception("该用户不存在");
        }
        //获取当天的前一天
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String specifiedDay = sdf.format(date);
        Date dateBefore = DateUtil.getSpecifiedDayBefore(specifiedDay);
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
        }else {
            //连续7天签到
            if(signNum.getContinueDays()==6){
                signNum.setContinueDays(0);
                //用户活跃值
                user.setActiveNum(user.getActiveNum()+3L);
            }else {
                signNum.setContinueDays(signNum.getContinueDays()+1);
                //用户活跃值
                user.setActiveNum(user.getActiveNum()+1L);
            }
            signNumService.updateById(signNum);
        }
        if(signNum.getContinueDays()==0){
            signDays=7;
        }else {
            signDays=signNum.getContinueDays();
        }
        // TODO: 比较level,得出用户当前所在的level,以及后续业务处理

        userService.updateById(user);
        map.put("signDays",signDays);
        map.put("activeNum",user.getActiveNum());
        map.put("level",user.getPerLevel());
        return map;
    }
}
