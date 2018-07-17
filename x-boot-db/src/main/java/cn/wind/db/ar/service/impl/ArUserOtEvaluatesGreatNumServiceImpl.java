package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserOtEvaluatesGreatNum;
import cn.wind.db.ar.dao.ArUserOtEvaluatesGreatNumMapper;
import cn.wind.db.ar.service.IArUserOtEvaluatesGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 纹纹达人用户评论点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Service
public class ArUserOtEvaluatesGreatNumServiceImpl extends ServiceImpl<ArUserOtEvaluatesGreatNumMapper, ArUserOtEvaluatesGreatNum> implements IArUserOtEvaluatesGreatNumService {

    @Override
    public ArUserOtEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long otEvaluateId, Long userId) {
        return this.baseMapper.findOneByEvaluteIdAndUserId(otEvaluateId,userId);
    }
}
