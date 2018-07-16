package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkEvaluates;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图库找图用户评价数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface ArUserSkEvaluatesMapper extends BaseMapper<ArUserSkEvaluates> {

    List<ArUserSkEvaluates> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserSkEvaluates findOneInSecondEvaluate(Map<String, Object> map);
}
