package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSkGreatNum;
import cn.wind.db.ar.dao.ArUserSkGreatNumMapper;
import cn.wind.db.ar.service.IArUserSkGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图库找图点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Service
public class ArUserSkGreatNumServiceImpl extends ServiceImpl<ArUserSkGreatNumMapper, ArUserSkGreatNum> implements IArUserSkGreatNumService {

    @Override
    public ArUserSkGreatNum findOneByGalleryIdAndUserId(Long skGalleryId, Long userId) {
        return this.baseMapper.findOneByGalleryIdAndUserId(skGalleryId,userId);
    }
}
