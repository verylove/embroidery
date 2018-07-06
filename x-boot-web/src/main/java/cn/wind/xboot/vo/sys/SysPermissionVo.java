package cn.wind.xboot.vo.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: SysPermissionVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/25
 */
@Data
public class SysPermissionVo {
    private Long id;
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
    private Boolean expand = true;
    private Boolean checked = false;
    private Boolean selected = false;
}
