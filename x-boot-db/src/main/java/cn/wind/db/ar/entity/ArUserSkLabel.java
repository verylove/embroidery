package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图库找图标签数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sk_label")
public class ArUserSkLabel extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * label名
     */
    private String name;
    /**
     * 等级 1-父级 2-子级
     */
    private Integer level;
    /**
     * 所属层级 level为1时为0
     */
    private Long pid;


}
