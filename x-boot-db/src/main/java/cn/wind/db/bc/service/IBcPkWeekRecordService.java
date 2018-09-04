package cn.wind.db.bc.service;

import cn.wind.db.bc.entity.BcPkWeekRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 主播直播周数据记录表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface IBcPkWeekRecordService extends IService<BcPkWeekRecord> {

    BcPkWeekRecord findOneByConditions(Map<String, Object> map);
}
