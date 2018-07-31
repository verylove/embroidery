package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDyEvaluatesGreatNum;
import cn.wind.db.ar.dao.ArUserDyEvaluatesGreatNumMapper;
import cn.wind.db.ar.service.IArUserDyEvaluatesGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 动态作品用户评论点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDyEvaluatesGreatNumServiceImpl extends ServiceImpl<ArUserDyEvaluatesGreatNumMapper, ArUserDyEvaluatesGreatNum> implements IArUserDyEvaluatesGreatNumService {

    @Override
    public ArUserDyEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long dyEvaluateId, Long userId) {
        return this.baseMapper.findOneByEvaluteIdAndUserId(dyEvaluateId,userId);
    }
}
