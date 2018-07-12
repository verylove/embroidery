package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSpEvaluates;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户特价纹身评论数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface ArUserSpEvaluatesMapper extends BaseMapper<ArUserSpEvaluates> {

    List findAllByConditions(Page page, Map<String, Object> map);

    ArUserSpEvaluates findOneInSecondEvaluate(Map<String, Object> map3);
}
