package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArSignAudit;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 用户签约保障申请数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface ArSignAuditMapper extends BaseMapper<ArSignAudit> {

    ArSignAudit findOneByConditions(Map<String, Object> map);
}
