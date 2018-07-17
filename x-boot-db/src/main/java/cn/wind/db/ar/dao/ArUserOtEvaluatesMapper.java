package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserOtEvaluates;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 纹纹达人用户评价数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface ArUserOtEvaluatesMapper extends BaseMapper<ArUserOtEvaluates> {

    List<ArUserOtEvaluates> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserOtEvaluates findOneInSecondEvaluate(Map<String, Object> map);
}
