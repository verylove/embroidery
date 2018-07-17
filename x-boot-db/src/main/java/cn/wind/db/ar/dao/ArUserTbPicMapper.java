package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserTbPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户贴吧话题图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserTbPicMapper extends BaseMapper<ArUserTbPic> {

    String RESULT_COLUMN = "id,tb_topic_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_tb_pic where tb_topic_id = #{tpTopicId}")
    List<ArUserTbPic> findAllByTopicId(@Param("tpTopicId") Long tpTopicId);
}
