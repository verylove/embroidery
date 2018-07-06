package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user")
public class SysUser extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户是否未过期
     */
    private Boolean accountNonExpired;
    /**
     * 登录凭据是否未过期
     */
    private Boolean credentialsNonExpired;
    /**
     * 账户是否未锁定
     */
    private Boolean accountNonLocked;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 用户状态 1 正常 -1 软删除
     */
    private Integer status;

    private Integer type;
    /**
     * 锁定时间
     */
    private Date lockTime;
    @TableField(exist=false)
    private  List<SysRole> roles;
    @TableField(exist=false)
    private  List<SysPermission> permissions;
    @TableField(exist=false)
    private  List<SysUserGroup> groups;
    @TableField(exist=false)
    private  SysUserInfo userInfo;
}
