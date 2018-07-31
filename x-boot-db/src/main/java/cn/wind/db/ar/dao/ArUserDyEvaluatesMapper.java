package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDyEvaluates;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动态作品用户评价数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDyEvaluatesMapper extends BaseMapper<ArUserDyEvaluates> {

    List<ArUserDyEvaluates> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserDyEvaluates findOneInSecondEvaluate(Map<String, Object> map);
}
