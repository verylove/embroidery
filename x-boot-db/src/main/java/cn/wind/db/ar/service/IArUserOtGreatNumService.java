package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserOtGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 纹纹达人点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface IArUserOtGreatNumService extends IService<ArUserOtGreatNum> {

    ArUserOtGreatNum findOneByOtTattooIdAndUserId(Long otTattooId, Long userId);
}
