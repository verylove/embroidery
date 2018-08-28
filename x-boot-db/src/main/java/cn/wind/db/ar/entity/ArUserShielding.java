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
 * 用户屏蔽数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_shielding")
public class ArUserShielding extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 屏蔽的主动方
     */
    private Long userId;
    /**
     * 被屏蔽的一方
     */
    private Long shieldingId;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;


}
