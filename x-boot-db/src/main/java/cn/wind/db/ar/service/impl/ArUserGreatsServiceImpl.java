package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserGreats;
import cn.wind.db.ar.dao.ArUserGreatsMapper;
import cn.wind.db.ar.service.IArUserGreatsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户被点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-20
 */
@Service
public class ArUserGreatsServiceImpl extends ServiceImpl<ArUserGreatsMapper, ArUserGreats> implements IArUserGreatsService {

    @Override
    public ArUserGreats findByUserIdAndGreatId(Long userId, Long userId1) {
        return this.baseMapper.findByUserIdAndGreatId(userId,userId1);
    }
}
