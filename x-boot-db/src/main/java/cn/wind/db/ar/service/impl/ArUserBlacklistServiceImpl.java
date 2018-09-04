package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserBlacklist;
import cn.wind.db.ar.dao.ArUserBlacklistMapper;
import cn.wind.db.ar.service.IArUserBlacklistService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户拉黑数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
@Service
public class ArUserBlacklistServiceImpl extends ServiceImpl<ArUserBlacklistMapper, ArUserBlacklist> implements IArUserBlacklistService {

    @Override
    public ArUserBlacklist findOneByUserIdAndBlackId(Long userId, Long toUserId) {
        return this.baseMapper.findOneByUserIdAndBlackId(userId,toUserId);
    }
}
