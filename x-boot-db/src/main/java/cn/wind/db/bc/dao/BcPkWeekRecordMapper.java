package cn.wind.db.bc.dao;

import cn.wind.db.bc.entity.BcPkWeekRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 主播直播周数据记录表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface BcPkWeekRecordMapper extends BaseMapper<BcPkWeekRecord> {

    BcPkWeekRecord findOneByConditions(Map<String, Object> map);
}
