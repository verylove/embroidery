package cn.wind.db.bc.entity;

import java.math.BigDecimal;

import cn.wind.mybatis.common.IdEntity;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主播直播周数据记录表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_pk_week_record")
public class BcPkWeekRecord extends IdEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主播ID
     */
    private Long bcUserId;
    /**
     * 本周胜率
     */
    private BigDecimal pkWins = new BigDecimal("0.00");
    /**
     * 本周胜利场数
     */
    private Integer winNum=0;
    /**
     * 本周失败场数
     */
    private Integer failureNum=0;
    /**
     * 本周平局场数
     */
    private Integer drawNum=0;
    /**
     * 本周收益
     */
    private Long liveEarnings=0L;
    /**
     * 周编号
     */
    private Integer weekNo;
    /**
     * 年编号
     */
    private Integer yearNo;
    /**
     * 1-大咖 0-娱乐
     */
    private Integer category=1;


}
