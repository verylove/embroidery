package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkGallery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图库找图数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface ArUserSkGalleryMapper extends BaseMapper<ArUserSkGallery> {

    List<ArUserSkGallery> findAllByConditions(Pagination page, Map<Object, Object> map);

    ArUserSkGallery findOneByConditions(Map<String, Object> map);
}
