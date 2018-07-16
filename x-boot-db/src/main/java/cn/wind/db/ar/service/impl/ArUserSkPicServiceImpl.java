package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSkPic;
import cn.wind.db.ar.dao.ArUserSkPicMapper;
import cn.wind.db.ar.service.IArUserSkPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 图库找图图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Service
public class ArUserSkPicServiceImpl extends ServiceImpl<ArUserSkPicMapper, ArUserSkPic> implements IArUserSkPicService {

    @Override
    public List<ArUserSkPic> findAllByGalleryId(Long id) {
        return this.baseMapper.findAllByGalleryId(id);
    }
}
