package cn.wind.db.sr.dao;

import cn.wind.db.sr.entity.SrLevels;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 等级数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface SrLevelsMapper extends BaseMapper<SrLevels> {

    String RESULT_COLUMN = "id,level,score,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_sr_levels where level = #{perLevel} limit 1")
    SrLevels findOneByLevel(@Param("perLevel") Integer perLevel);
}
