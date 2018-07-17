package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserOtEvaluatesGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 纹纹达人用户评论点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface IArUserOtEvaluatesGreatNumService extends IService<ArUserOtEvaluatesGreatNum> {

    ArUserOtEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long otEvaluateId, Long userId);
}
