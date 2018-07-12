package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    public ArUser addUserByIdentity(String phone, String password, Integer type) {

        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPhone(phone);
        user.setIcon(
                "https://apollobucket.oss-cn-beijing.aliyuncs.com/head_img.png");
        user.setPassword(password);
        user.setIdentity(type);
        userService.insert(user);
        return user;
    }

    public ArUser addUser(String openId, String phone, Integer type) {
        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPhone(phone);
        user.setPassword("a111111");
        user.setIcon(
                "https://apollobucket.oss-cn-beijing.aliyuncs.com/head_img.png");
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
}
