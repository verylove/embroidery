package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_role")
public class SysRole extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 代号
     */
    private String name;
    /**
     * 名称
     */
    private String title;
    private String description;
    private Integer sort;
    private Boolean enabled;

    @TableField(exist=false)
    private   List<SysPermission> permissions;
}
