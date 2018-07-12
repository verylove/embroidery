package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserZxPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户纹身咨询图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface ArUserZxPicMapper extends BaseMapper<ArUserZxPic> {

    String RESULT_COLUMN = "id,zx_tattoo_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_zx_pic where zx_tattoo_id = #{tattooId}")
    List<ArUserZxPic> findAllByTattooId(@Param("tattooId") Long tattooId);
}
