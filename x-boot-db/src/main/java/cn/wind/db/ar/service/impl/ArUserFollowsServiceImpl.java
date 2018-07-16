package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserFollows;
import cn.wind.db.ar.dao.ArUserFollowsMapper;
import cn.wind.db.ar.service.IArUserFollowsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关注数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserFollowsServiceImpl extends ServiceImpl<ArUserFollowsMapper, ArUserFollows> implements IArUserFollowsService {


    @Override
    public ArUserFollows findOneByUserIdAndFollowId(Long userId, Long followId) {
        return this.baseMapper.findOneByUserIdAndFollowId(userId,followId);
    }
}
