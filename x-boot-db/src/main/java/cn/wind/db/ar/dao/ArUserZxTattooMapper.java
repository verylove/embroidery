package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserZxTattoo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户纹身咨询数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface ArUserZxTattooMapper extends BaseMapper<ArUserZxTattoo> {

    List<ArUserZxTattoo> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserZxTattoo findOneByConditions(Map<String, Object> map);

    List<ArUserZxTattoo> findAllZxByCondition(Pagination page, Map<String, Object> map);
}
