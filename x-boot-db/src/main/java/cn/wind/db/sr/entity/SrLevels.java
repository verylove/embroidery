package cn.wind.db.sr.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 等级数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_sr_levels")
public class SrLevels extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 等级
     */
    private Integer level;
    /**
     * 等级对应的分值要求
     */
    private Integer score;


}
