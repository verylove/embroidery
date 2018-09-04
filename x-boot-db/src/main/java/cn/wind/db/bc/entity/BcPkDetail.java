package cn.wind.db.bc.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主播PK详情数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_pk_detail")
public class BcPkDetail extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 邀请的用户ID
     */
    private Long inviteUserId;
    /**
     * 被邀请的用户ID
     */
    private Long enInviteUserId;
    /**
     * 胜利用户ID （-1平局）
     */
    private Long winner;
    /**
     * 邀请用户总的礼物价值
     */
    private BigDecimal inviteAmount;
    /**
     * 被邀请的用户的礼物价值
     */
    private BigDecimal enInviteAmount;
    /**
     * 1-大咖 0-娱乐
     */
    private Integer category=1;
}
