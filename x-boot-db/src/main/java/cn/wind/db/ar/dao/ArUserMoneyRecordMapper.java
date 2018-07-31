package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserMoneyRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 用户金额变动记录数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface ArUserMoneyRecordMapper extends BaseMapper<ArUserMoneyRecord> {

    ArUserMoneyRecord findOnebyContions(Map<String, Object> map);
}
