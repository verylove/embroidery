package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSignRecord;
import cn.wind.db.ar.dao.ArUserSignRecordMapper;
import cn.wind.db.ar.service.IArUserSignRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户签到记录表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
@Service
public class ArUserSignRecordServiceImpl extends ServiceImpl<ArUserSignRecordMapper, ArUserSignRecord> implements IArUserSignRecordService {

    @Override
    public List<ArUserSignRecord> findAllBetweenDaysAndUserId(Long userId, LocalDate firstDayByNow, LocalDate lastDayByNow) {
        return this.baseMapper.findAllBetweenDaysAndUserId(userId,firstDayByNow,lastDayByNow);
    }

    @Override
    public ArUserSignRecord findAllByDateAndUserId(Long userId, LocalDate dateBefore) {
        return this.baseMapper.findAllByDateAndUserId(userId,dateBefore);
    }
}
