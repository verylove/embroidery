package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserTbGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户贴吧话题点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserTbGreatNumService extends IService<ArUserTbGreatNum> {

    ArUserTbGreatNum findOneByTopicIdAndUserId(Long tbTopicId, Long userId);
}
