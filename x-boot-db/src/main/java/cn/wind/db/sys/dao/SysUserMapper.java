package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 公共返回字段
     */
    String RESULT_COLUMN = "id,uuid,username,password,account_non_expired,credentials_non_expired" +
            ",account_non_locked,enabled,status,create_time,create_by,modify_time" +
            ",modify_by,lock_time";

    /**
     * 更新accountNonLocked属性
     *
     * @param accountNonLocked
     * @param username
     */
    @Update("update t_sys_user set accountNonLocked=#{accountNonLocked} where username=#{username} ")
    void updateForAccountNonLocked(@Param("accountNonLocked") Boolean accountNonLocked, @Param("username") String username);

    /**
     * 根据用户ID获取全部角色
     *
     * @param userId
     * @return
     */
    @Select("select * from t_sys_role r inner join t_sys_user_role ru on r.id = ru.role_id where ru.user_id = #{userId}")
    List<SysRole> findByUserId(Long userId);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Select("select " + RESULT_COLUMN + " from t_sys_user where username=#{username}")
    SysUser findByUsername(@Param("username") String username);

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    SysUser findUserInfoById(@Param("userId")Long userId);

    /**
     * 根据查询条件查询
     * @param page
     * @param var1
     * @return
     */
    List<SysUser> findByCondition(Pagination page, Map<String, Object> var1);

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    @Update("update t_sys_user set password=#{password} where id=#{id}")
    Integer updatePassowrdById(@Param("password")String password,@Param("id")Long id);
}
