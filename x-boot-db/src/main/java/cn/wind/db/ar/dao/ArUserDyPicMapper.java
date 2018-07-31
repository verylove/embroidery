package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDyPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 动态作品图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDyPicMapper extends BaseMapper<ArUserDyPic> {

    String RESULT_COLUMN = "id,dy_works_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_dy_pic where dy_works_id = #{dyWorksId}")
    List<ArUserDyPic> findAllByDyWorksId(@Param("dyWorksId") Long dyWorksId);
}
