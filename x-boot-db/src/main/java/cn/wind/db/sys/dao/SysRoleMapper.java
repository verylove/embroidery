package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("SELECT r.* FROM t_sys_role r INNER JOIN t_sys_user_role ru ON r.id = ru.role_id WHERE ru.user_id =#{userId} "
            +" union "
            +"select r.* from t_sys_role r INNER JOIN t_sys_user_group_role_ship ugrs on r.id=ugrs.role_id INNER JOIN t_sys_user_group_ship ugs on ugs.group_id=ugrs.group_id where ugs.user_id=#{userId} "
    )
    List<SysRole> findByUserId(@Param("userId")Long userId);
}
