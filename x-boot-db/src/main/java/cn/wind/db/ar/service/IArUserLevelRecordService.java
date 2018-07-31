package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserLevelRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户level新增记录数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface IArUserLevelRecordService extends IService<ArUserLevelRecord> {

    ArUserLevelRecord findOneByConditons(Map<String, Object> map);

    List<ArUserLevelRecord> findAllByConditionsYesterDay(Map<String, Object> map);
}
