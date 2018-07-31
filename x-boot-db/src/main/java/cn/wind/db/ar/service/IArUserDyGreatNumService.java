package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDyGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 动态作品点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDyGreatNumService extends IService<ArUserDyGreatNum> {

    ArUserDyGreatNum findOneByDyWorksIdAndUserId(Long dyWorksId, Long userId);
}
