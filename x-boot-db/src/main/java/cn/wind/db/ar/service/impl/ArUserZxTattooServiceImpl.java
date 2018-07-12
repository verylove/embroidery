package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserZxTattoo;
import cn.wind.db.ar.dao.ArUserZxTattooMapper;
import cn.wind.db.ar.service.IArUserZxTattooService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户纹身咨询数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Service
public class ArUserZxTattooServiceImpl extends ServiceImpl<ArUserZxTattooMapper, ArUserZxTattoo> implements IArUserZxTattooService {

    @Override
    public Page<ArUserZxTattoo> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserZxTattoo findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
