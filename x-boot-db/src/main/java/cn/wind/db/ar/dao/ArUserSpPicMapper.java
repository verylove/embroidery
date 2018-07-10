package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSpPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 特价纹身相关图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface ArUserSpPicMapper extends BaseMapper<ArUserSpPic> {

    String RESULT_COLUMN = "id,special_tattoo_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN +" from cx_ar_user_sp_pic where special_tattoo_id = #{tattooId}")
    List<ArUserSpPic> findAllByTattoId(@Param("tattooId") Long tattooId);
}
