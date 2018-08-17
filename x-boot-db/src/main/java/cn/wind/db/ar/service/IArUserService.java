package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUser;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

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

    ArUser findOneByWxOpenId(String openId);

    ArUser findOneByQqOpenId(String openId);

    List<ArUser> findAllByIdIn(List<Long> userIds);

    Page<ArUser> findAllByConditions(Page page, Map<String, Object> map);
}
