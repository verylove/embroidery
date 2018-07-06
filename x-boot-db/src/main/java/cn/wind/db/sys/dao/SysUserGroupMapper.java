package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUserGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {
    /**
     * 根据用户ID获取用户组
     * @param userId
     * @return
     */
    @Select("select ug.* from t_sys_user_group ug join t_sys_user_group_ship ugs  on ug.id=ugs.group_id where ugs.user_id=#{userId}")
    List<SysUserGroup> findByUserId(@Param("userId") Long userId);

    /**
     * 根据单个用户组ID获取角色
     * @param groupId
     * @return
     */
    @Select("select * from t_sys_role r join t_sys_user_group_role_ship ugrs on r.id=ugrs.role_id where ugrs.group_id=#{groupId} ")
    List<SysRole> findByGroupId(Long groupId);

    /**
     * 根据多个用户组ID获取角色
     * @param groupIds
     * @return
     */
    @Select("<script>"
            + "select * from t_sys_role r join t_sys_user_group_role_ship ugrs on r.id=ugrs.role_id where ugrs.group_id in "
            + "<foreach item='item' index='index' collection='groupIds' open='(' separator=',' close=')' >"
            + " #{item}"
            + "</foreach>"
            + "</script>")
    List<SysRole> findByGroupIds(@Param("groupIds") List<Long> groupIds);
}
