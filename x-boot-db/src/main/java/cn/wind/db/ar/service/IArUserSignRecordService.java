package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSignRecord;
import com.baomidou.mybatisplus.service.IService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户签到记录表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
public interface IArUserSignRecordService extends IService<ArUserSignRecord> {

    List<ArUserSignRecord> findAllBetweenDaysAndUserId(Long userId, LocalDate firstDayByNow, LocalDate lastDayByNow);

    ArUserSignRecord findAllByDateAndUserId(Long userId, Date dateBefore);
}
