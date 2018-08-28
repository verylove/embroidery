package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserShielding;
import cn.wind.db.ar.dao.ArUserShieldingMapper;
import cn.wind.db.ar.service.IArUserShieldingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户屏蔽数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-21
 */
@Service
public class ArUserShieldingServiceImpl extends ServiceImpl<ArUserShieldingMapper, ArUserShielding> implements IArUserShieldingService {

    @Override
    public List<Long> findAllShieldIdsByUserId(long userId) {
        return this.baseMapper.findAllShieldIdsByUserId(userId);
    }

    @Override
    public List<Long> findAllUserIdsByShieldId(long shieldId) {
        return this.baseMapper.findAllUserIdsByShieldId(shieldId);
    }

    @Override
    public ArUserShielding findOneByUserIdAndShieldId(long userId, Long shieldId) {
        return this.baseMapper.findOneByUserIdAndShieldId(userId,shieldId);
    }
}
