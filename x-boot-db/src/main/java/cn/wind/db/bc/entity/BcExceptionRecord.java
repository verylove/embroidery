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
 * 用户打赏主播礼物记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_exception_record")
public class BcExceptionRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 主播ID
     */
    private Long bcUserId;
    /**
     * 1-大咖直播 2-娱乐 3-大咖（PK时，此时记录下哪次PK）4-娱乐（PK）
     */
    private Integer bcType=1;
    /**
     * 赠送的礼物价值
     */
    private BigDecimal bcAmount=new BigDecimal("0.00");
    /**
     * 周编号
     */
    private Integer weekNo;
    /**
     * 年编号
     */
    private Integer yearNo;


}
