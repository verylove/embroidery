package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSpPic;
import cn.wind.db.ar.dao.ArUserSpPicMapper;
import cn.wind.db.ar.service.IArUserSpPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价纹身相关图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Service
public class ArUserSpPicServiceImpl extends ServiceImpl<ArUserSpPicMapper, ArUserSpPic> implements IArUserSpPicService {

    @Override
    public List<ArUserSpPic> findAllByTattoId(Long id) {
        return this.baseMapper.findAllByTattoId(id);
    }
}
