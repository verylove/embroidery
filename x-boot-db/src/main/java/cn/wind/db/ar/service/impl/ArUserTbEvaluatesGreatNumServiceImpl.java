package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserTbEvaluatesGreatNum;
import cn.wind.db.ar.dao.ArUserTbEvaluatesGreatNumMapper;
import cn.wind.db.ar.service.IArUserTbEvaluatesGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户话题贴吧评论点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserTbEvaluatesGreatNumServiceImpl extends ServiceImpl<ArUserTbEvaluatesGreatNumMapper, ArUserTbEvaluatesGreatNum> implements IArUserTbEvaluatesGreatNumService {

    @Override
    public ArUserTbEvaluatesGreatNum findOneByEvaluteIdAndUserId(Long tbEvaluateId, Long userId) {
        return this.baseMapper.findOneByEvaluteIdAndUserId(tbEvaluateId,userId);
    }
}
