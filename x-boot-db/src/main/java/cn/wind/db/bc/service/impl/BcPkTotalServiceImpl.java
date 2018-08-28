package cn.wind.db.bc.service.impl;

import cn.wind.db.bc.entity.BcPkTotal;
import cn.wind.db.bc.dao.BcPkTotalMapper;
import cn.wind.db.bc.service.IBcPkTotalService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 主播PK总数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-28
 */
@Service
public class BcPkTotalServiceImpl extends ServiceImpl<BcPkTotalMapper, BcPkTotal> implements IBcPkTotalService {

    @Override
    public BcPkTotal findOneByUserId(long userId) {
        return this.baseMapper.findOneByUserId(userId);
    }

    @Override
    public Long findRankInCity(Map<String,Object> map) {
        return this.baseMapper.findRankInCity(map);
    }
}
