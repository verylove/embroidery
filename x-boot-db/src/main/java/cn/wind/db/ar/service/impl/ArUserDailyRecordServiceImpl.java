package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDailyRecord;
import cn.wind.db.ar.dao.ArUserDailyRecordMapper;
import cn.wind.db.ar.service.IArUserDailyRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 用户每日新增数据记录数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDailyRecordServiceImpl extends ServiceImpl<ArUserDailyRecordMapper, ArUserDailyRecord> implements IArUserDailyRecordService {

    @Override
    public ArUserDailyRecord findOneByUserIdAndDate(Long userId, LocalDate date) {
        return this.baseMapper.findOneByUserIdAndDate(userId,date);
    }
}
