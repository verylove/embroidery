package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDyWorks;
import cn.wind.db.ar.dao.ArUserDyWorksMapper;
import cn.wind.db.ar.service.IArUserDyWorksService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 作品/动态数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDyWorksServiceImpl extends ServiceImpl<ArUserDyWorksMapper, ArUserDyWorks> implements IArUserDyWorksService {

    @Override
    public Page<ArUserDyWorks> findAllStoreByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllStoreByConditions(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllArtistByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllArtistByConditions(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllDyFocusByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllDyFocusByConditions(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllCircleWorkOrderByTime(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllCircleWorkOrderByTime(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllCircleWorkOrderByGreatNum(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllCircleWorkOrderByGreatNum(page,map));
    }

    @Override
    public ArUserDyWorks findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public Page<ArUserDyWorks> findAllStoreWorksForManager(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllStoreWorksForManager(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllStoreWorksForPerson(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllStoreWorksForPerson(page,map));
    }

    @Override
    public Page<ArUserDyWorks> findAllPerDynamic(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllPerDynamic(page,map));
    }
}
