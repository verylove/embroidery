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
 * 主播PK总数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_pk_total")
public class BcPkTotal extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 房间ID
     */
    private Long roomId;
    /**
     * 胜率
     */
    private BigDecimal pkWins=new BigDecimal("0.00");
    /**
     * 胜利场数
     */
    private Integer winNum=0;
    /**
     * 失败场数
     */
    private Integer failureNum=0;
    /**
     * 平局场数
     */
    private Integer drawNum=0;
    /**
     * 直播城市
     */
    private Long cityId;
    /**
     * 直播收益
     */
    private Long liveEarnings=0L;


}
