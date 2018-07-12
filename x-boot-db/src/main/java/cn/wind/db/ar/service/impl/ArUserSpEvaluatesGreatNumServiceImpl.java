package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSpEvaluatesGreatNum;
import cn.wind.db.ar.dao.ArUserSpEvaluatesGreatNumMapper;
import cn.wind.db.ar.service.IArUserSpEvaluatesGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Service
public class ArUserSpEvaluatesGreatNumServiceImpl extends ServiceImpl<ArUserSpEvaluatesGreatNumMapper, ArUserSpEvaluatesGreatNum> implements IArUserSpEvaluatesGreatNumService {


    @Override
    public ArUserSpEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long spEvaluateId, Long userId) {
        return this.baseMapper.findOneByEvaluteIdAndUserId(spEvaluateId,userId);
    }
}
