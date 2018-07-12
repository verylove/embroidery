package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSpGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface IArUserSpGreatNumService extends IService<ArUserSpGreatNum> {

    ArUserSpGreatNum findOneByTattooIdAndUserId(Long spTattooId, Long userId);
}
