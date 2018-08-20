package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArStoreAduit;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户店铺认证申请数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface ArStoreAduitMapper extends BaseMapper<ArStoreAduit> {

    ArStoreAduit findOneByConditions(Map<String, Object> map);

    List<ArStoreAduit> findAllByConditions(Pagination page, Map<String, Object> map);
}
