package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDailyRecord;
import com.baomidou.mybatisplus.service.IService;

import java.time.LocalDate;

/**
 * <p>
 * 用户每日新增数据记录数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDailyRecordService extends IService<ArUserDailyRecord> {

    ArUserDailyRecord findOneByUserIdAndDate(Long userId, LocalDate date);
}
