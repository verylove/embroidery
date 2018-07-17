package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserOtGreatNum;
import cn.wind.db.ar.dao.ArUserOtGreatNumMapper;
import cn.wind.db.ar.service.IArUserOtGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 纹纹达人点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Service
public class ArUserOtGreatNumServiceImpl extends ServiceImpl<ArUserOtGreatNumMapper, ArUserOtGreatNum> implements IArUserOtGreatNumService {

    @Override
    public ArUserOtGreatNum findOneByOtTattooIdAndUserId(Long otTattooId, Long userId) {
        return this.baseMapper.findOneByOtTattooIdAndUserId(otTattooId,userId);
    }
}
