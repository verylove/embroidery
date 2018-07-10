package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSignNum;
import cn.wind.db.ar.dao.ArUserSignNumMapper;
import cn.wind.db.ar.service.IArUserSignNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户累计签到次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
@Service
public class ArUserSignNumServiceImpl extends ServiceImpl<ArUserSignNumMapper, ArUserSignNum> implements IArUserSignNumService {

    @Override
    public ArUserSignNum findOneByUserId(Long userId) {
        return this.baseMapper.findOneByUserId(userId);
    }
}
