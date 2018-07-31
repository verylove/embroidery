package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserLevelRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户level新增记录数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface ArUserLevelRecordMapper extends BaseMapper<ArUserLevelRecord> {

    ArUserLevelRecord findOneByConditons(Map<String, Object> map);

    List<ArUserLevelRecord> findAllByConditionsYesterDay(Map<String, Object> map);
}
