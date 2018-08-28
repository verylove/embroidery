package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserShielding;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户屏蔽数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-21
 */
public interface IArUserShieldingService extends IService<ArUserShielding> {

    List<Long> findAllShieldIdsByUserId(long userId);

    List<Long> findAllUserIdsByShieldId(long shieldId);

    ArUserShielding findOneByUserIdAndShieldId(long l, Long shieldId);
}
