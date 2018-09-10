package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkLabel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图库找图标签数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface ArUserSkLabelMapper extends BaseMapper<ArUserSkLabel> {

    String RESULT_COLUMN = "id,name,level,pid,create_time,modify_time,create_by,modify_by";

    @Select("select "+ RESULT_COLUMN + " from cx_ar_user_sk_label where id in #{labelIds}")
    List<ArUserSkLabel> findAllByIdsIn(@Param("labelIds") List<Long> labelIds);

    List<ArUserSkLabel> findAllLabelByCondition(Pagination page, Map<String, Object> map);

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sk_label where level = 1")
    List<ArUserSkLabel> findAllParentLabel();
}
