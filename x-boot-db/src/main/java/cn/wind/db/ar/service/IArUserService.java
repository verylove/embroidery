package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * App用户表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-06
 */
public interface IArUserService extends IService<ArUser> {

    ArUser findOneByPhone(String phone);

}
