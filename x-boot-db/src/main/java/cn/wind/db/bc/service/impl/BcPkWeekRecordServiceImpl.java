package cn.wind.db.bc.service.impl;

import cn.wind.db.bc.entity.BcPkWeekRecord;
import cn.wind.db.bc.dao.BcPkWeekRecordMapper;
import cn.wind.db.bc.service.IBcPkWeekRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 主播直播周数据记录表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Service
public class BcPkWeekRecordServiceImpl extends ServiceImpl<BcPkWeekRecordMapper, BcPkWeekRecord> implements IBcPkWeekRecordService {

    @Override
    public BcPkWeekRecord findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
