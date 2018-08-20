package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.dao.ArStoreAduitMapper;
import cn.wind.db.ar.service.IArStoreAduitService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户店铺认证申请数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Service
public class ArStoreAduitServiceImpl extends ServiceImpl<ArStoreAduitMapper, ArStoreAduit> implements IArStoreAduitService {

    @Override
    public ArStoreAduit findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public Page<ArStoreAduit> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }
}
