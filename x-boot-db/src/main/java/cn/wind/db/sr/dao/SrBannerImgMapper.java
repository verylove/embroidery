package cn.wind.db.sr.dao;

import cn.wind.db.sr.entity.SrBannerImg;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface SrBannerImgMapper extends BaseMapper<SrBannerImg> {

    String RESULT_COLUMN = "id,img,category,type,img_key,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_sr_banner_img where category = #{category}")
    List<SrBannerImg> findAllByCategory(@Param("category") Integer category);

    List<SrBannerImg> findAllByConditions(Pagination page, Map<String, Object> map);
}
