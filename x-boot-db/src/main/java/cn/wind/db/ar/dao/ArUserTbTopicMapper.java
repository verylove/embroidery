package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserTbTopic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户贴吧话题数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserTbTopicMapper extends BaseMapper<ArUserTbTopic> {

    List<ArUserTbTopic> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserTbTopic findOneByConditons(Map<String, Object> map);
}
