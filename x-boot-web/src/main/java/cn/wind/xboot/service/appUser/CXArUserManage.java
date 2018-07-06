package cn.wind.xboot.service.appUser;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/6 19:23
 * @Description:
 */
@Service
@Transactional
public class CXArUserManage {

    @Autowired
    private IArUserService userService;

    public boolean addUser(String phone, String password) {
        boolean result = false;

        ArUser user = new ArUser();
        user.setAccount(phone);
        user.setPassword(password);
        result = userService.insert(user);
        return result;
    }
}
