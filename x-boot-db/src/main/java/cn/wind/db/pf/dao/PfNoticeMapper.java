package cn.wind.db.pf.dao;

import cn.wind.db.pf.entity.PfNotice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 平台通知 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-07-02
 */
public interface PfNoticeMapper extends BaseMapper<PfNotice> {
    @Update("update t_pf_notice set readed=1 where id=#{id}")
    Integer updateReadedById(@Param("id")Long id);

    @Update("<script>"
            + "update t_pf_notice set readed=1 WHERE id in "
            + "<foreach item='item' index='index' collection='ids'      open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    Integer updateReadedByIds(@Param("ids")List ids);
    @Select("select * from t_pf_notice where del_flag=#{delFlag}")
    List<PfNotice>  findByDelFlag(@Param("delFlag")Integer delFlag);
    @Select("select count(1) from t_pf_notice where del_flag=#{delFlag}")
    Integer countByDelFlag(@Param("delFlag")Integer delFlag);
    @Update("<script>"
            + "update t_pf_notice set del_flag=0 WHERE id in "
            + "<foreach item='item' index='index' collection='ids'      open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    Integer updateDelFlagByIds(@Param("ids")List ids);
}
