package cn.wind.xboot.service.appUser;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ArUser addUser(String phone, String password) {

        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPhone(phone);
        user.setIcon(
                "https://apollobucket.oss-cn-beijing.aliyuncs.com/head_img.png");
        user.setPassword(password);
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
}
