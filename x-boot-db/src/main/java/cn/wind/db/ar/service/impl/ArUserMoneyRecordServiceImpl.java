package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.dao.ArUserMoneyRecordMapper;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户金额变动记录数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Service
public class ArUserMoneyRecordServiceImpl extends ServiceImpl<ArUserMoneyRecordMapper, ArUserMoneyRecord> implements IArUserMoneyRecordService {

    @Override
    public ArUserMoneyRecord findOnebyContions(Map<String, Object> map) {
        return this.baseMapper.findOnebyContions(map);
    }
}
