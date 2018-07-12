package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSpGreatNum;
import cn.wind.db.ar.dao.ArUserSpGreatNumMapper;
import cn.wind.db.ar.service.IArUserSpGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Service
public class ArUserSpGreatNumServiceImpl extends ServiceImpl<ArUserSpGreatNumMapper, ArUserSpGreatNum> implements IArUserSpGreatNumService {

    @Override
    public ArUserSpGreatNum findOneByTattooIdAndUserId(Long spTattooId, Long userId) {
        return this.baseMapper.findOneByTattooIdAndUserId(spTattooId,userId);
    }
}
