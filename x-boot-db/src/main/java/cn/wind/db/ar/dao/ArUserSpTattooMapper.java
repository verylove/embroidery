package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSpTattoo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 特价纹身数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface ArUserSpTattooMapper extends BaseMapper<ArUserSpTattoo> {

    List<ArUserSpTattoo> findByCoordinates(Pagination page, Map<String, Object> map);

    List<ArUserSpTattoo> findAll(Pagination page);

    ArUserSpTattoo findOneById(@Param(value = "spTattooId") Long spTattooId);
}
