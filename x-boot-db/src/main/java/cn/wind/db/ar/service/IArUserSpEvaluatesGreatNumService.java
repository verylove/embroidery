package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSpEvaluatesGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface IArUserSpEvaluatesGreatNumService extends IService<ArUserSpEvaluatesGreatNum> {

    ArUserSpEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long spEvaluateId, Long userId);
}
