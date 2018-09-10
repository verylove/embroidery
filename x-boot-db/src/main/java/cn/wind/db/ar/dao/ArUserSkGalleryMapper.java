package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkGallery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<ArUserSkGallery> findAllSkByCondition(Pagination page, Map<String, Object> map);

    @Select("select count(*) from cx_ar_user_sk_gallery where label = #{skLabelId} and status = 1")
    Integer countByLabel(@Param("skLabelId") Long skLabelId);
}
