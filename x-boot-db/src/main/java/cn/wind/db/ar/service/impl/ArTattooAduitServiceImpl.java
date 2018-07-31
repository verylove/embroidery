package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArTattooAduit;
import cn.wind.db.ar.dao.ArTattooAduitMapper;
import cn.wind.db.ar.service.IArTattooAduitService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户纹身师申请数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Service
public class ArTattooAduitServiceImpl extends ServiceImpl<ArTattooAduitMapper, ArTattooAduit> implements IArTattooAduitService {

    @Override
    public ArTattooAduit findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
