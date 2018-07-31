package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDyEvaluatesGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 动态作品用户评论点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDyEvaluatesGreatNumService extends IService<ArUserDyEvaluatesGreatNum> {

    ArUserDyEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long dyEvaluateId, Long userId);
}
