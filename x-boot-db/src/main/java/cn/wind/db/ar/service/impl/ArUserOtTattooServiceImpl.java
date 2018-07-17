package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserOtTattoo;
import cn.wind.db.ar.dao.ArUserOtTattooMapper;
import cn.wind.db.ar.service.IArUserOtTattooService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 纹纹达人数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Service
public class ArUserOtTattooServiceImpl extends ServiceImpl<ArUserOtTattooMapper, ArUserOtTattoo> implements IArUserOtTattooService {

    @Override
    public Page<ArUserOtTattoo> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserOtTattoo findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
