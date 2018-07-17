package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserOtPic;
import cn.wind.db.ar.dao.ArUserOtPicMapper;
import cn.wind.db.ar.service.IArUserOtPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 纹纹达人图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Service
public class ArUserOtPicServiceImpl extends ServiceImpl<ArUserOtPicMapper, ArUserOtPic> implements IArUserOtPicService {

    @Override
    public List<ArUserOtPic> findAllByOtTattooId(Long id) {
        return this.baseMapper.findAllByOtTattooId(id);
    }
}
