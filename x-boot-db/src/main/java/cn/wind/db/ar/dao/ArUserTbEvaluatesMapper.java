package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserTbEvaluates;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户贴吧话题评论数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserTbEvaluatesMapper extends BaseMapper<ArUserTbEvaluates> {

    List<ArUserTbEvaluates> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserTbEvaluates findOneInSecondEvaluate(Map<String, Object> map);
}
