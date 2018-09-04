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
 * 用户拉黑数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_blacklist")
public class ArUserBlacklist extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 拉黑的主动方
     */
    private Long userId;
    /**
     * 被拉黑的一方
     */
    private Long blackId;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;


}
