package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 图库找图图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface ArUserSkPicMapper extends BaseMapper<ArUserSkPic> {

    String RESULT_COLUMN = "id,seek_gallery_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sk_pic where seek_gallery_id = #{galleryId}")
    List<ArUserSkPic> findAllByGalleryId(@Param("galleryId") Long id);
}
