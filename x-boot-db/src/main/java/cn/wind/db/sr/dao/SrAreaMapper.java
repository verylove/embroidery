package cn.wind.db.sr.dao;

import cn.wind.db.sr.entity.SrArea;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中国区域数据表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-07-06
 */
public interface SrAreaMapper extends BaseMapper<SrArea> {

    String RESULT_COLUMN = "id,abbr_name,alias,center,city_code,code,level,name,status";

    List<SrArea> findAllByConditions(Map<String, Object> map);

    @Select("select "+RESULT_COLUMN+" from cx_sr_area where name like #{name} and status = 1 and level = 2")
    SrArea findByName(@Param("name")String name);

    List<SrArea> findAllByIdsIn(List<Long> cityIds);
}
