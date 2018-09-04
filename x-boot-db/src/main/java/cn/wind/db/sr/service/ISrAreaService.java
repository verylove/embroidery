package cn.wind.db.sr.service;

import cn.wind.db.sr.entity.SrArea;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中国区域数据表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-07-06
 */
public interface ISrAreaService extends IService<SrArea> {

    List<SrArea> findAllByConditions(Map<String, Object> map);

    SrArea findByName(String s);

    List<SrArea> findAllByIdsIn(List<Long> cityIds);
}
