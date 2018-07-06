package cn.wind.xboot.vo.sys;

import lombok.Data;

/**
 * <p>Title: SysRoleVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/26
 */
@Data
public class SysRoleVo {
    private Long id;
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
}
