package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户组
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user_group")
public class SysUserGroup extends AuditEntity {

    private static final long serialVersionUID = 1L;
    private Long pId;
    private String name;
    private String title;
    private String type;
    @Version
    private Integer version;
    @TableField(exist=false)
    private List<SysRole> roles;
    @TableField(exist=false)
    private  List<SysUser> users;

}
