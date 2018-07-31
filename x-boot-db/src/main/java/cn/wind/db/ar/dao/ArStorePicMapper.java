package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArStorePic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 店铺室内环境图集数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface ArStorePicMapper extends BaseMapper<ArStorePic> {

    String RESULT_COLUMN = "id,img, store_id,create_time,modify_time,create_by,modify_by";

    @Select("select " +RESULT_COLUMN+" from cx_ar_store_pic where store_id = #{storeId}")
    List<ArStorePic> findAllByStoreId(@Param("storeId") Long storeId);
}
