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
 * 用户银行卡信息数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_bank")
public class ArUserBank extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 卡号
     */
    private String bankCard;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 所属银行
     */
    private String bankName;
    /**
     * 支行信息
     */
    private String branchName;
    /**
     * 1-默认 0-不默认
     */
    private Integer type;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;


}
