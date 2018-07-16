package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkGreatNum;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 图库找图点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkGreatNumService extends IService<ArUserSkGreatNum> {

    String RESULT_COLUMN = "id,seek_gallery_id,user_id,author_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sk_great_num where seek_gallery_id = #{skGalleryId} and user_id = #{userId} limit 1")
    ArUserSkGreatNum findOneByGalleryIdAndUserId(@Param("skGalleryId") Long skGalleryId, @Param("userId") Long userId);
}
