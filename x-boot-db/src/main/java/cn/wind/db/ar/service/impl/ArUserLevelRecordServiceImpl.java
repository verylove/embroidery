package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserLevelRecord;
import cn.wind.db.ar.dao.ArUserLevelRecordMapper;
import cn.wind.db.ar.service.IArUserLevelRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户level新增记录数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Service
public class ArUserLevelRecordServiceImpl extends ServiceImpl<ArUserLevelRecordMapper, ArUserLevelRecord> implements IArUserLevelRecordService {

    @Override
    public ArUserLevelRecord findOneByConditons(Map<String, Object> map) {
        return this.baseMapper.findOneByConditons(map);
    }

    @Override
    public List<ArUserLevelRecord> findAllByConditionsYesterDay(Map<String, Object> map) {
        return this.baseMapper.findAllByConditionsYesterDay(map);
    }
}
