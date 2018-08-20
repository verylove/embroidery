package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArTattooAduit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户纹身师申请数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface ArTattooAduitMapper extends BaseMapper<ArTattooAduit> {

    ArTattooAduit findOneByConditions(Map<String, Object> map);

    List<ArTattooAduit> findAllByConditions(Pagination page, Map<String, Object> map);
}
