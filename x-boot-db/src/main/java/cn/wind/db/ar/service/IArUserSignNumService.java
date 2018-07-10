package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSignNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户累计签到次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
public interface IArUserSignNumService extends IService<ArUserSignNum> {

    ArUserSignNum findOneByUserId(Long userId);
}
