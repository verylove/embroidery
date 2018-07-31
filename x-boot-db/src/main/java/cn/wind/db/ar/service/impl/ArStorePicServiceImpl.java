package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArStorePic;
import cn.wind.db.ar.dao.ArStorePicMapper;
import cn.wind.db.ar.service.IArStorePicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 店铺室内环境图集数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Service
public class ArStorePicServiceImpl extends ServiceImpl<ArStorePicMapper, ArStorePic> implements IArStorePicService {

    @Override
    public List<ArStorePic> findAllByStoreId(Long storeId) {
        return this.baseMapper.findAllByStoreId(storeId);
    }
}
