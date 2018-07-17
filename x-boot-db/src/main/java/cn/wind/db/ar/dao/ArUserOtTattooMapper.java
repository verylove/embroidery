package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserOtTattoo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 纹纹达人数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface ArUserOtTattooMapper extends BaseMapper<ArUserOtTattoo> {

    List<ArUserOtTattoo> findAllByConditions(Pagination page, Map<String, Object> map);

    ArUserOtTattoo findOneByConditions(Map<String, Object> map);
}
