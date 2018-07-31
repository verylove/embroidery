package cn.wind.db.sr.service.impl;

import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.dao.SrLevelsMapper;
import cn.wind.db.sr.service.ISrLevelsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 等级数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Service
public class SrLevelsServiceImpl extends ServiceImpl<SrLevelsMapper, SrLevels> implements ISrLevelsService {

    @Override
    public SrLevels findOneByLevel(Integer perLevel) {
        return this.baseMapper.findOneByLevel(perLevel);
    }
}
