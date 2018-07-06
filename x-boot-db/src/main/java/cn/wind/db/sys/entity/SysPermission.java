package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_permission")
public class SysPermission extends AuditEntity {

    private static final long serialVersionUID = 1L;

    private Long pId;
    /**
     * 资源代号
     */
    private String name;

    private String permission;
    /**
     * 资源名称
     */
    private String title;
    /**
     * 资源路径
     */
    private String path;
    private Integer level;
    private BigDecimal sort;
    private String icon;
    /**
     * 资源类型
     */
    private String type;
    private String buttonType;
    private String component;
    /**
     * 描述
     */
    private String description;
    private Boolean enabled;
    @TableField(exist=false)
    private  List<String>  buttonTypes;
    @TableField(exist=false)
    private  List<SysPermission> children;
    @TableField(exist=false)
    private Boolean expand = true;
    @TableField(exist=false)
    private Boolean checked = false;
    @TableField(exist=false)
    private Boolean selected = false;
}
