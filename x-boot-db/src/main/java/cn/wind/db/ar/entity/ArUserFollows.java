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
 * 用户关注数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_follows")
public class ArUserFollows extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关注的主动方
     */
    private Long userId;
    /**
     * 被关注的一方
     */
    private Long followId;
    /**
     * 1-有效 0-失效
     */
    private Integer status;


}
