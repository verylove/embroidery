package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSkGallery;
import cn.wind.db.ar.dao.ArUserSkGalleryMapper;
import cn.wind.db.ar.service.IArUserSkGalleryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 图库找图数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Service
public class ArUserSkGalleryServiceImpl extends ServiceImpl<ArUserSkGalleryMapper, ArUserSkGallery> implements IArUserSkGalleryService {

    @Override
    public Page<ArUserSkGallery> findAllByConditions(Page page, Map<Object, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }
}
