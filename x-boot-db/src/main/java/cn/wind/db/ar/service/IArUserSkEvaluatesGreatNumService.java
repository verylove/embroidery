package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkEvaluatesGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 图库找图用户评论点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkEvaluatesGreatNumService extends IService<ArUserSkEvaluatesGreatNum> {

    ArUserSkEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long skEvaluateId, Long userId);
}
