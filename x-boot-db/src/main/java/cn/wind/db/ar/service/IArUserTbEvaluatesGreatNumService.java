package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserTbEvaluatesGreatNum;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户话题贴吧评论点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserTbEvaluatesGreatNumService extends IService<ArUserTbEvaluatesGreatNum> {

    ArUserTbEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long tbEvaluateId, Long userId);
}
