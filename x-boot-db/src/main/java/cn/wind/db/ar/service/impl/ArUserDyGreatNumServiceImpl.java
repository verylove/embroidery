package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDyGreatNum;
import cn.wind.db.ar.dao.ArUserDyGreatNumMapper;
import cn.wind.db.ar.service.IArUserDyGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 动态作品点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDyGreatNumServiceImpl extends ServiceImpl<ArUserDyGreatNumMapper, ArUserDyGreatNum> implements IArUserDyGreatNumService {

    @Override
    public ArUserDyGreatNum findOneByDyWorksIdAndUserId(Long dyWorksId, Long userId) {
        return this.baseMapper.findOneByDyWorksIdAndUserId(dyWorksId,userId);
    }
}
