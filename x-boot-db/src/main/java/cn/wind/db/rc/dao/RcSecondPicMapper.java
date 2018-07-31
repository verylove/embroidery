package cn.wind.db.rc.dao;

import cn.wind.db.rc.entity.RcSecondPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 二手市场图片数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface RcSecondPicMapper extends BaseMapper<RcSecondPic> {

    String RESULT_COLUMN = "id,second_transact_id,img,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_rc_second_pic where second_transact_id = #{secondTransactId}")
    List<RcSecondPic> findAllBySecondTransactId(@Param("secondTransactId") Long secondTransactId);
}
