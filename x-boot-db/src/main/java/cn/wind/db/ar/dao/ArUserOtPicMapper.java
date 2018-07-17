package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserOtPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 纹纹达人图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface ArUserOtPicMapper extends BaseMapper<ArUserOtPic> {

    String RESULT_COLUMN = "id,ot_tattoo_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_ot_pic where ot_tattoo_id = #{otTattooId}")
    List<ArUserOtPic> findAllByOtTattooId(@Param("otTattooId") Long otTattooId);
}
