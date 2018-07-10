package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSpTattoo;
import cn.wind.db.ar.dao.ArUserSpTattooMapper;
import cn.wind.db.ar.service.IArUserSpTattooService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 特价纹身数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Service
public class ArUserSpTattooServiceImpl extends ServiceImpl<ArUserSpTattooMapper, ArUserSpTattoo> implements IArUserSpTattooService {

    @Override
    public Page<ArUserSpTattoo> findByCoordinates(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findByCoordinates(page,map));
    }

    @Override
    public Page<ArUserSpTattoo> findAll(Page page) {
        return page.setRecords(this.baseMapper.findAll(page));
    }
}
